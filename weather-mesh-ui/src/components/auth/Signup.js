import React, {Component} from 'react'
import {Link, Navigate} from 'react-router-dom'
import {Button, Form, Grid, Menu, Message, Segment} from 'semantic-ui-react'
import AuthContext from './AuthContext'
import {advertApi} from '../util/AdvertApi'
import {handleLogError} from '../util/ErrorHandler'

class Signup extends Component {
    static contextType = AuthContext

    state = {
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        isLoggedIn: false,
        isError: false,
        errorMessage: ''
    }

    componentDidMount() {
        const Auth = this.context
        const isLoggedIn = Auth.userIsAuthenticated()
        this.setState({isLoggedIn})
    }

    handleInputChange = (e, {name, value}) => {
        this.setState({[name]: value})
    }

    handleSubmit = (e) => {
        e.preventDefault()
        const {firstName, lastName, email, password} = this.state

        if (!(firstName && lastName && email && password)) {
            this.setState({
                isError: true,
                errorMessage: 'Пожалуйста, заполните все поля!'
            })
            return
        }

        const user = {firstName, lastName, email, password}

        advertApi.signup(user)
            .then(response => {
                const {id, firstName, lastName, role} = response.data
                const authdata = window.btoa(email + ':' + password)
                const user = {id, firstName, lastName, role, authdata}

                const Auth = this.context
                Auth.userLogin(user)

                this.setState({
                    email: '',
                    password: '',
                    isLoggedIn: true,
                    isError: false,
                    errorMessage: ''
                })
            })
            .catch(error => {
                handleLogError(error)
                if (error.response && error.response.data) {
                    const errorData = error.response.data
                    let errorMessage = 'Некорректные поля'
                    if (errorData.status === 409) {
                        errorMessage = errorData.message
                    } else if (errorData.status === 400) {
                        errorMessage = errorData.errors[0].defaultMessage
                    }
                    this.setState({
                        isError: true,
                        errorMessage
                    })
                }
            })
    }

    render() {
        const {isLoggedIn, isError, errorMessage} = this.state
        if (isLoggedIn) {
            return <Navigate to='/'/>
        } else {
            return (
                <Grid textAlign='center'>
                    <Grid.Column style={{maxWidth: 450}}>
                        <Form size='large' onSubmit={this.handleSubmit}>
                            <Segment>
                                <Form.Input
                                    fluid
                                    autoFocus
                                    name='firstName'
                                    icon='user'
                                    iconPosition='left'
                                    placeholder='Имя'
                                    onChange={this.handleInputChange}
                                />
                                <Form.Input
                                    fluid
                                    name='lastName'
                                    icon='address card'
                                    iconPosition='left'
                                    placeholder='Фамилия'
                                    onChange={this.handleInputChange}
                                />
                                <Form.Input
                                    fluid
                                    name='email'
                                    icon='at'
                                    iconPosition='left'
                                    placeholder='Почта'
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
                                <Button color='blue' fluid size='large'>Зарегистрироваться</Button>
                            </Segment>
                        </Form>
                        <Message>{`Уже есть аккаунт? `}
                            <Menu.Item as={Link} to="/login" >Войти</Menu.Item>
                        </Message>
                        {isError && <Message negative>{errorMessage}</Message>}
                    </Grid.Column>
                </Grid>
            )
        }
    }
}

export default Signup