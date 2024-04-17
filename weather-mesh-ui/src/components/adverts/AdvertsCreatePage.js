import React, {Component} from 'react'
import {Navigate} from 'react-router-dom'
import {Button, Dimmer, Form, Grid, Header, Loader, Message, Segment} from 'semantic-ui-react'
import AuthContext from '../auth/AuthContext'
import {advertApi} from "../util/AdvertApi";
import {handleLogError} from "../util/ErrorHandler";

class AdvertsCreatePage extends Component {
    static contextType = AuthContext

    state = {
        title: '',
        county: '',
        city: '',
        district: '',
        street: '',
        houseNumber: '',
        description: '',
        image: null,

        isUser: true,

        isLoading: false,
        isError: false,
        errorMessage: ''
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()
        const isUser = user.role === 'USER'
        const userId = user.id

        this.setState({isUser})
        this.setState({userId})
    }

    handleInputChange = (e, {name, value}) => {
        this.setState({[name]: value})
    }

    fileInputRef = React.createRef();

    handleFileChange = e => {
        this.setState({ image: e.target.files[0] })
    };

    handleSubmit = async () => {
        const {title, description, county, city} = this.state

        if (!(title && description && county && city)) {
            this.setState({
                isError: true,
                errorMessage: 'Пожалуйста, предоставьте как минимум заголовок, область, город и описание.'
            })
            return
        }

        this.setState({isLoading: true})
        try {
             await this.handleCreateAdvert()
        } catch(error) {
            handleLogError(error)
        } finally {
            this.setState({isLoading: false})
            this.clear()
        }
    }

    async handleCreateAdvert() {
        const Auth = this.context
        const user = Auth.getUser()
        const advert = this.getBuiltAdvert(user.id)

        await advertApi.createAdvert(user, advert)
            .then(response => {
                this.handleUploadImage(response.data.id)
            })
    }

    handleUploadImage(advertId) {
        const Auth = this.context
        const user = Auth.getUser()
        const image = this.state.image

        advertApi.uploadImage(user, image, advertId)
            .then(response => {
                return response.data
            })
    }

    getBuiltAdvert = (id) => {
        return {
            title: this.state.title,
            description: this.state.description,
            userId: id,
            address:  {
                county: this.state.county,
                city: this.state.city,
                district: this.state.district,
                street: this.state.street,
                houseNumber: this.state.houseNumber
            }
        }
    }

    clear = () => {
        this.setState({
            title: '',
            description: '',
            image: null
        })
    }

    render() {
        const {isLoading, isUser, isError, errorMessage} = this.state

        if (isLoading) {
            return (
                <Segment basic style={{marginTop: window.innerHeight / 3}}>
                    <Dimmer active inverted page={true}>
                        <Loader inverted size='huge'>Создание объявления...</Loader>
                    </Dimmer>
                </Segment>
            )
        } else if (!isUser) {
            return <Navigate to='/'/>
        } else {
            return (
                <Grid textAlign='center'>
                    <Grid.Column style={{maxWidth: 750}}>
                        <Form size='large' onSubmit={null}>
                            <Segment>
                                <Header as='h2' color={"violet"} textAlign={"center"}>
                                    Форма создания объявления
                                </Header>
                                <Form.Input
                                    fluid
                                    autoFocus
                                    name='title'
                                    icon='paragraph'
                                    iconPosition='left'
                                    placeholder='Заголовок объявления'
                                    onChange={this.handleInputChange}
                                />
                                <Form.Input
                                    fluid
                                    name='county'
                                    icon='map marker alternate'
                                    iconPosition='left'
                                    placeholder='Область'
                                    onChange={this.handleInputChange}
                                />
                                <Form.Input
                                    fluid
                                    name='city'
                                    icon='factory'
                                    iconPosition='left'
                                    placeholder='Город'
                                    onChange={this.handleInputChange}
                                />
                                <Form.Input
                                    fluid
                                    name='district'
                                    icon='map'
                                    iconPosition='left'
                                    placeholder='Район'
                                    onChange={this.handleInputChange}
                                />
                                <Form.Input
                                    fluid
                                    name='street'
                                    icon='map signs'
                                    iconPosition='left'
                                    placeholder='Улица'
                                    onChange={this.handleInputChange}
                                />
                                <Form.Input
                                    fluid
                                    name='houseNumber'
                                    icon='home'
                                    iconPosition='left'
                                    placeholder='Дом'
                                    onChange={this.handleInputChange}
                                />
                                <Form.TextArea
                                    name='description'
                                    placeholder='Описание'
                                    onChange={this.handleInputChange}
                                />
                                <Segment style={{marginTop: "20px", marginBottom: "20px"}}>
                                    <Button
                                        content="Выбрать фотографию"
                                        labelPosition="left"
                                        icon="attach"
                                        onClick={() => this.fileInputRef.current.click()}
                                    />
                                    <input
                                        ref={this.fileInputRef}
                                        type="file"
                                        hidden
                                        onChange={this.handleFileChange}
                                    />
                                    { (this.state.image) &&
                                        <Message positive>Фотография прикреплена!</Message>
                                    }
                                </Segment>
                                <Button
                                    color='green'
                                    fluid
                                    size='huge'
                                    type={"button"}
                                    onClick={() => this.handleSubmit()}
                                >
                                    Создать объявление
                                </Button>
                            </Segment>
                        </Form>

                        {isError && <Message negative>{errorMessage}</Message>}
                    </Grid.Column>
                </Grid>
            )
        }
    }
}

export default AdvertsCreatePage