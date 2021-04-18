import { Card, Col, Row } from 'antd';
import React, { useEffect, useState } from 'react';
import Task from '../components/Task';
import TaskEntry from '../components/TaskEntry';
import { api } from '../http';
import { TaskListContext } from '../contexts/TaskListContext';

const TaskList = () => {
	const [tasks, setTasks] = useState([]);

	useEffect(() => {
		api
			.get('/tasks')
			.then((v) => {
				setTasks(v.data.data);
			})
			.catch((e) => console.log(e));
	}, []);

	return (
		<Row justify='center'>
			<Col span={12} className='list-container'>
				<TaskListContext.Provider value={{ tasks, setTasks }}>
					<Card title='My Task List'>
						{tasks && tasks.length > 0 ? (
							tasks.map((v, k) => <Task key={k} task={v} />)
						) : (
							<div>No Tasks Found</div>
						)}
						<TaskEntry />
					</Card>
				</TaskListContext.Provider>
			</Col>
		</Row>
	);
};

export default TaskList;
