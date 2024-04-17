import React, {Component} from 'react'
import {Container, Dimmer, Grid, Loader, Segment} from 'semantic-ui-react'
import {advertApi} from '../util/AdvertApi'
import {handleLogError} from '../util/ErrorHandler'
import Navbar from "./Navbar";
import HomeHeader from "./HomeHeader";

class Home extends Component {
    state = {
        isLoading: false
    }

    async componentDidMount() {
        this.setState({isLoading: true})
        try {
            let response = await advertApi.getUsersCount()
            const usersCount = response.data

            response = await advertApi.getAdvertsCount()
            const advertsCount = response.data

            this.setState({usersCount, advertsCount})
        } catch (error) {
            handleLogError(error)
        } finally {
            this.setState({isLoading: false})
        }
    }

    render() {
        const {isLoading} = this.state
        if (isLoading) {
            return (
                <Segment basic style={{marginTop: window.innerHeight / 2}}>
                    <Dimmer active inverted page={true}>
                        <Loader inverted size='huge'>Loading</Loader>
                    </Dimmer>
                </Segment>
            )
        } else {
            const {page} = this.props;
            return (
                <Container>
                    <HomeHeader/>
                    <Grid stackable color={"black"} inverted>
                        <Grid.Row>
                            <Grid.Column width={5}>
                                <Container>
                                    <Navbar/>
                                </Container>
                            </Grid.Column>

                            <Grid.Column width={11}>
                                <Container>
                                    {page}
                                </Container>
                            </Grid.Column>
                        </Grid.Row>
                    </Grid>
                </Container>
            )
        }
    }
}

export default Home