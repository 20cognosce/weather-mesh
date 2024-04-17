import React, {Component} from 'react'
import {Navigate} from 'react-router-dom'
import {Button, Container, Dimmer, Form, Loader, Message, Segment} from 'semantic-ui-react'
import AuthContext from './AuthContext'
import {advertApi} from '../util/AdvertApi'
import {handleLogError} from '../util/ErrorHandler'

class Login extends Component {
    static contextType = AuthContext

    state = {
        login: '',
        password: '',
        isLoggedIn: false,
        isError: false,
        isLoading: false
    }

    componentDidMount() {
        const Auth = this.context
        const isLoggedIn = Auth.userIsAuthenticated()
        this.setState({isLoggedIn})
    }

    handleInputChange = (e, {name, value}) => {
        this.setState({[name]: value})
    }

    handleSubmit = async (e) => {
        e.preventDefault()

        const {email, password} = this.state
        if (!(email && password)) {
            this.setState({isError: true})
            return
        }

        this.setState({isLoading: true})
        await advertApi
            .login(email, password)
            .then(response => {
                const {id, firstName, lastName, role} = response.data
                const authdata = window.btoa(email + ':' + password)
                const user = {id, firstName, lastName, role, authdata}

                const Auth = this.context
                Auth.userLogin(user)

                this.setState({email: '', password: '', isLoggedIn: true, isError: false})
            })
            .catch(error => {
                handleLogError(error)
                this.setState({isError: true})
            })
        this.setState({isLoading: false})
    }

    render() {
        const {isLoggedIn, isError, isLoading} = this.state
        if (isLoading) {
            return (
                <Segment basic style={{marginTop: window.innerHeight / 3}}>
                    <Dimmer active inverted page={true}>
                        <Loader inverted size='huge'>Вход в систему...</Loader>
                    </Dimmer>
                </Segment>
            )
        } else if (isLoggedIn) {
            return <Navigate to={'/'}/>
        } else {
            return (
                <Container>
                    <Form style={{display: 'flex', justifyContent: 'center'}}
                          size='massive'
                          onSubmit={this.handleSubmit}>
                        <Segment style={{minWidth: 500}} inverted>
                            <h3 style={{display: 'flex', justifyContent: 'center'}}> Введите свои учетные данные </h3>
                            <Form.Input
                                fluid
                                autoFocus
                                name='email'
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