import React, {Component} from 'react'
import {Navigate} from 'react-router-dom'
import {Container} from 'semantic-ui-react'
import AdvertList from './AdvertList'
import AuthContext from '../auth/AuthContext'
import {advertApi} from '../util/AdvertApi'
import {handleLogError} from '../util/ErrorHandler'

class AdvertsPage extends Component {
    static contextType = AuthContext

    state = {
        adverts: [],
        advertTitleToFind: '',
        isUser: true,
        isAdvertsLoading: false
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()
        const isUser = user.role === 'USER'

        this.setState({isUser})
        this.handleFindAdverts()
    }

    handleInputChange = (e, {name, value}) => {
        this.setState({[name]: value})
    }

    handleGetImageUrlById = (id) => {
        return advertApi.getImageUrlById(id);
    }

    handleFindAdverts = () => {
        const Auth = this.context
        const user = Auth.getUser()

        const title = this.state.advertTitleToFind
        this.setState({isAdvertsLoading: true})
        advertApi.findAdvertByTitle(user, title)
            .then(response => {
                const adverts = response.data
                this.setState({adverts})
            })
            .catch(error => {
                handleLogError(error)
                this.setState({adverts: []})
            })
            .finally(() => {
                this.setState({isAdvertsLoading: false})
            })
    }

    render() {
        if (!this.state.isUser) {
            return <Navigate to='/'/>
        } else {
            const {isAdvertsLoading, adverts, advertTitleToFind} = this.state
            return (
                <Container>
                    <AdvertList
                        adverts={adverts}
                        advertTitleToFind={advertTitleToFind}
                        isAdvertsLoading={isAdvertsLoading}

                        handleInputChange={this.handleInputChange}
                        handleFindAdverts={this.handleFindAdverts}
                        handleGetImageUrlById={this.handleGetImageUrlById}
                    />
                </Container>
            )
        }
    }
}

export default AdvertsPage