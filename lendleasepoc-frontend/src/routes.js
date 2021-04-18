import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import TaskList from './layouts/TaskList';
class Routes extends React.Component {
	render() {
		return (
			<BrowserRouter>
				<Switch>
					<Route exact path={'/'} component={TaskList} />
				</Switch>
			</BrowserRouter>
		);
	}
}

export default Routes;
