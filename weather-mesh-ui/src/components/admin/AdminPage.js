import React, {Component} from 'react'
import {Navigate} from 'react-router-dom'
import {Container} from 'semantic-ui-react'
import AuthContext from '../auth/AuthContext'
import {advertApi} from '../util/AdvertApi'
import AdminTab from './AdminTab'
import {handleLogError} from '../util/ErrorHandler'

class AdminPage extends Component {
    static contextType = AuthContext

    state = {
        users: [],
        adverts: [],
        advertTitleToFind: '',
        userEmailToFind: '',
        isAdmin: true,
        isUsersLoading: false,
        isAdvertsLoading: false,
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()
        const isAdmin = user.role === 'ADMIN'
        this.setState({isAdmin})

        this.handleFindUsers()
        this.handleFindAdverts()
    }

    handleInputChange = (e, {name, value}) => {
        this.setState({[name]: value})
    }

    handleFindUsers = () => {
        const Auth = this.context
        const user = Auth.getUser()

        const email = this.state.userEmailToFind
        this.setState({isUsersLoading: true})
        advertApi.findUsersByEmail(user, email)
            .then(response => {
                this.setState({users: response.data})
            })
            .catch(error => {
                handleLogError(error)
                this.setState({users: []})
            })
            .finally(() => {
                this.setState({isUsersLoading: false})
        })
    }

    handleFindAdverts = () => {
        const Auth = this.context
        const user = Auth.getUser()

        const title = this.state.advertTitleToFind
        this.setState({isAdvertsLoading: true})
        advertApi.findAdvertByTitle(user, title)
            .then(response => {
                this.setState({adverts: response.data})
            })
            .catch(error => {
                handleLogError(error)
                this.setState({adverts: []})
            })
            .finally(() => {
                this.setState({isAdvertsLoading: false})
            })
    }

    handleDeleteUser = (id) => {
        const Auth = this.context
        const user = Auth.getUser()

        this.setState({isUsersLoading: true})
        advertApi.deleteUser(user, id)
            .then(() => {
                this.handleFindUsers()
            })
            .catch(error => {
                handleLogError(error)
            })
    }

    handleActivateUser = (id) => {
        const Auth = this.context
        const user = Auth.getUser()

        this.setState({isUsersLoading: true})
        advertApi.activateUser(user, id)
            .then(() => {
                this.handleFindUsers()
            })
            .catch(error => {
                handleLogError(error)
            })
    }

    handleGetImageUrlById = (id) => {
        return advertApi.getImageUrlById(id);
    }

    handleDeleteAdvert = (id) => {
        const Auth = this.context
        const user = Auth.getUser()

        this.setState({isAdvertsLoading: true})
        advertApi.deleteAdvert(user, id)
            .then(() => {
                this.handleFindAdverts()
            })
            .catch(error => {
                handleLogError(error)
            })
    }

    render() {
        if (!this.state.isAdmin) {
            return <Navigate to='/'/>
        } else {
            const {
                users,
                adverts,
                advertTitleToFind,
                userEmailToFind,
                isUsersLoading,
                isAdvertsLoading
            } = this.state
            return (
                <Container>
                    <AdminTab
                        handleInputChange={this.handleInputChange}

                        isUsersLoading={isUsersLoading}
                        users={users}
                        userEmailToFind={userEmailToFind}
                        handleFindUsers={this.handleFindUsers}
                        handleDeleteUser={this.handleDeleteUser}
                        handleActivateUser={this.handleActivateUser}

                        isAdvertsLoading={isAdvertsLoading}
                        adverts={adverts}
                        advertTitleToFind={advertTitleToFind}
                        handleFindAdverts={this.handleFindAdverts}
                        handleGetImageUrlById={this.handleGetImageUrlById}
                        handleDeleteAdvert={this.handleDeleteAdvert}
                        />
                </Container>
            )
        }
    }
}

export default AdminPage