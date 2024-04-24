import React, {Component} from 'react'
import {Container, Dimmer, Grid, Loader, Menu, MenuHeader, MenuItem, Segment} from 'semantic-ui-react'
import {authApi} from '../api/AuthApi'
import {weatherApi} from "../api/WeatherApi";
import {circuitBreakerApi} from "../api/CircuitBreakerApi";

class Home extends Component {
    state = {
        authStatus: '',
        weatherStatus: '',
        circuitBreakerStatus: '',
        isLoading: false
    }

    async componentDidMount() {
        this.setState({isLoading: true})
        try {
            const authHealthResponse = await authApi.getHealth()
            this.setState({authStatus: authHealthResponse.data})
        } catch (error) {
            this.setState({authStatus: {'status': 'DOWN'}})
        }

        try {
            const weatherHealthResponse = await weatherApi.getHealth()
            this.setState({weatherStatus: weatherHealthResponse.data})
        } catch (error) {
            this.setState({weatherStatus: {'status': 'DOWN'}})
        }

        try {
            const circuitBreakerHealthResponse = await circuitBreakerApi.getHealth()
            this.setState({circuitBreakerStatus: circuitBreakerHealthResponse.data})
        } catch (error) {
            this.setState({circuitBreakerStatus: {'status': 'DOWN'}})
        }

        this.setState({isLoading: false})
    }

    render() {
        const {isLoading, authStatus, weatherStatus, circuitBreakerStatus} = this.state

        if (isLoading) {
            return (
                <Segment basic style={{marginTop: window.innerHeight / 2}}>
                    <Dimmer active inverted page={true}>
                        <Loader inverted size='huge'>Loading</Loader>
                    </Dimmer>
                </Segment>
            )
        } else {
            return (
                <Container>
                    <Segment inverted>
                        <h3 style={{textAlign: 'center'}}> Актуальное состояние сервисов интегрированных в Weather Mesh </h3>
                    </Segment>

                    <Grid stackable color={"black"} inverted>
                        <Grid.Row>
                            <Grid.Column width={5}>
                                <Menu vertical size='massive' color={"black"} fluid inverted>
                                    <MenuItem style={{textAlign: 'center'}}>
                                        <MenuHeader>
                                            Сервис авторизации
                                        </MenuHeader>
                                        <br/>
                                        {formatStatus(authStatus)}
                                    </MenuItem>
                                </Menu>
                            </Grid.Column>

                            <Grid.Column width={5}>
                                <Menu vertical size='massive' color={"black"} fluid inverted>
                                    <MenuItem style={{textAlign: 'center'}}>
                                        <MenuHeader>
                                            Сервис показаний погоды
                                        </MenuHeader>
                                        <br/>
                                        {formatStatus(weatherStatus)}
                                    </MenuItem>
                                </Menu>
                            </Grid.Column>

                            <Grid.Column width={6}>
                                <Menu vertical size='massive' color={"black"} fluid inverted>
                                    <MenuItem style={{textAlign: 'center'}}>
                                        <MenuHeader>
                                            Сервис управления трафиком
                                        </MenuHeader>
                                        <br/>
                                        {formatStatus(circuitBreakerStatus)}
                                    </MenuItem>
                                </Menu>
                            </Grid.Column>
                        </Grid.Row>
                    </Grid>
                </Container>
            )
        }
    }
}

const formatStatus = (status) => {
    return status ? Object.keys(status)
        .map(key => (
            <React.Fragment key={key}>
                <span style={{fontWeight: 'bolder'}}>Статус: </span>
                <span style={{
                    fontWeight: 'bolder',
                    color: status[key] === 'UP' ? 'rgba(0, 255, 0, 0.8)' : 'rgba(255, 0, 0, 0.8)'
                }}>
                    {status[key]}
                </span>
            </React.Fragment>
        )) : '';
}

export default Home