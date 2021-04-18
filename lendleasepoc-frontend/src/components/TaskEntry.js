import React, { useContext } from 'react';
import { Button, Input, Row, Col, Form } from 'antd';
import { api } from '../http';
import { TaskListContext } from '../contexts/TaskListContext';

const TaskEntry = () => {
	const [form] = Form.useForm();
	const { setTasks } = useContext(TaskListContext);

	const requiredRule = { required: true, message: 'This field is required' };
	const submitToQueueURL = '/tasks/queue';
	const submitRegularURL = '/tasks';

	const handleSubmit = (url) => {
		const fetchTasks = () => {
			api
				.get('/tasks')
				.then((v) => setTasks(v.data.data))
				.catch((e) => console.log(e));
		};

		form
			.validateFields()
			.then((v) => {
				console.log('Submitting to: ' + url);
				api
					.post(url, v)
					.then((v) => {
						form.resetFields();
						fetchTasks();
					})
					.catch((e) => console.log(e));
			})
			.catch((e) => console.log(e));
	};

	return (
		<CenteredRow>
			<Col span={20}>
				<CenteredRow _className='padded-row'>
					<Col span={18}>
						<Form form={form} title='Add new Task'>
							<Form.Item name='name' label='Task Title' rules={[requiredRule]}>
								<Input placeholder='Title' />
							</Form.Item>
							<Form.Item
								name='description'
								label='Description'
								rules={[requiredRule]}>
								<Input.TextArea placeholder='Description..' />
							</Form.Item>
						</Form>
					</Col>
				</CenteredRow>
				<CenteredRow justify='center' _className='padded-row'>
					<Col span={12}>
						<CenteredRow>
							<Button
								type='danger'
								className='btnRegular'
								onClick={() => handleSubmit(submitRegularURL)}>
								Submit
							</Button>
						</CenteredRow>
					</Col>
					<Col span={12}>
						<CenteredRow>
							<Button
								type='primary'
								className='btnRegular'
								onClick={() => handleSubmit(submitToQueueURL)}>
								Submit to Queue
							</Button>
						</CenteredRow>
					</Col>
				</CenteredRow>
			</Col>
		</CenteredRow>
	);
};

const CenteredRow = (props) => {
	const { _className } = props;
	return (
		<Row className={_className ? _className : null} justify='center'>
			{props.children}
		</Row>
	);
};

export default TaskEntry;
