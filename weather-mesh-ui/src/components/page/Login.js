import React, {Component} from 'react'
import {Navigate} from 'react-router-dom'
import {Button, Container, Dimmer, Form, Loader, Message, Segment} from 'semantic-ui-react'
import AuthContext from '../auth/AuthContext'
import {authApi} from '../api/AuthApi'
import {handleLogError} from '../util/ErrorHandler'

class Login extends Component {
    static contextType = AuthContext

    state = {
        login: '',
        password: '',
        isAuthenticated: false,
        isError: false,
        isLoading: false
    }

    componentDidMount() {
        const AuthContext = this.context
        const isAuthenticated = AuthContext.isUserAuthenticated
        this.setState({isAuthenticated})
    }

    handleInputChange = (e, {name, value}) => {
        this.setState({[name]: value})
    }

    handleLogin = async (login, password) => {
        try {
            const tokenResponse = await authApi.postToken(login, window.btoa(password));
            const token = tokenResponse.data;

            const accountResponse = await authApi.getAccount(token);
            const {role} = accountResponse.data;
            if (role === 'UNAUTHORIZED') {
                this.setState({login: '', password: '', isAuthenticated: false, isError: true});
            } else {
                this.context.userLogin(token, login, role);
                this.setState({login: '', password: '', isAuthenticated: true, isError: false});
            }
        } catch (error) {
            handleLogError(error);
            this.setState({isError: true});
        }
    }

    handleSubmit = async (e) => {
        e.preventDefault()
        const {login, password} = this.state

        if (!(login && password)) {
            this.setState({isError: true})
            return
        }

        this.setState({isLoading: true})
        await this.handleLogin(login, password)
        this.setState({isLoading: false})
    }

    render() {
        const {isAuthenticated, isError, isLoading} = this.state
        if (isLoading) {
            return (
                <Segment basic style={{marginTop: window.innerHeight / 3}}>
                    <Dimmer active inverted page={true}>
                        <Loader inverted size='huge'>Вход в систему...</Loader>
                    </Dimmer>
                </Segment>
            )
        } else if (isAuthenticated) {
            return <Navigate to={'/'}/>
        } else {
            return (
                <Container>
                    <Form style={{display: 'flex', justifyContent: 'center'}}
                          size='massive'
                          onSubmit={this.handleSubmit}>
                        <Segment style={{minWidth: 500}} inverted>
                            <h3 style={{display: 'flex', justifyContent: 'center'}}> Введите свои учетные данные </h3>
                            <br/>
                            <Form.Input
                                fluid
                                autoFocus
                                name='login'
                                icon='user'
                                iconPosition='left'
                                placeholder='Логин'
                                onChange={this.handleInputChange}
                            />
                            <Form.Input
                                fluid
                                name='password'
                                icon='lock'
                                iconPosition='left'
                                placeholder='Пароль'
                                type='password'
                                onChange={this.handleInputChange}
                            />
                            <Button color='twitter' fluid size='large'>Войти</Button>
                        </Segment>
                    </Form>
                    {
                        isError &&
                        <Message negative>
                            Неверный логин или пароль
                        </Message>
                    }
                </Container>
            )
        }
    }
}

export default Login