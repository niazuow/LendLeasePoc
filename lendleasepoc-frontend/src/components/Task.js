import React, { useContext } from 'react';

import { Row, Col, Card, Button } from 'antd';
import { api } from '../http';
import { TaskListContext } from '../contexts/TaskListContext';

const Task = (props) => {
	const { task } = props;
	const { id, name, description } = task;
	const { setTasks } = useContext(TaskListContext);

	const handleComplete = () => {
		const fetchTasks = () => {
			api
				.get('/tasks')
				.then((v) => setTasks(v.data.data))
				.catch((e) => console.log(e));
		};

		api
			.delete(`/tasks/${id}`)
			.then((v) => {
				fetchTasks();
			})
			.catch((e) => console.log(e));
	};

	return (
		<Row justify='center' className='padded-row'>
			<Col span={18}>
				<Card
					title={name}
					extra={
						<Button type='danger' onClick={handleComplete}>
							Done
						</Button>
					}>
					{description}
				</Card>
			</Col>
		</Row>
	);
};
export default Task;
