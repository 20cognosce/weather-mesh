import React from 'react'
import {Container, Grid, List, Segment} from 'semantic-ui-react'

export default function Links() {
    return (
        <Container>
            <Segment inverted>
                <h3 style={{textAlign: 'center'}}> Полезные ссылки </h3>
            </Segment>

            <Segment inverted>
                <Grid columns={2} divided relaxed inverted>
                    <Grid.Row>
                        <Grid.Column width={2} verticalAlign='middle' textAlign="center">
                            <List.Icon name='github' size='big' style={{margin: 0}}/>
                        </Grid.Column>
                        <Grid.Column width={14}>
                            <List inverted>
                                <List.Item >
                                    <List.Header as='h3'>Github</List.Header>
                                    <List.Description as='a' href="https://github.com/20cognosce/weather-mesh">https://github.com/20cognosce/weather-mesh</List.Description>
                                </List.Item>
                            </List>
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column width={2} verticalAlign='middle' textAlign="center">
                            <List.Icon name='telegram' size='big' style={{margin: 0}}/>
                        </Grid.Column>
                        <Grid.Column width={14}>
                            <List inverted>
                                <List.Item>
                                    <List.Header as='h3'>Telegram</List.Header>
                                    <List.Description as='a' href="https://t.me/cognosce">https://t.me/cognosce</List.Description>
                                </List.Item>
                            </List>
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column width={2} verticalAlign='middle' textAlign="center">
                            <List.Icon name='vk' size='big' style={{margin: 0}}/>
                        </Grid.Column>
                        <Grid.Column width={14}>
                            <List inverted>
                                <List.Item>
                                    <List.Header as='h3'>Кафедра ИиППО</List.Header>
                                    <List.Description as='a' href="https://vk.com/ippo_it">https://vk.com/ippo_it</List.Description>
                                </List.Item>
                            </List>
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column width={2} verticalAlign='middle' textAlign="center">
                            <List.Icon size='big' style={{margin: 0}}>
                                <svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" viewBox="0 0 35 60">
                                    <path d="m15.105 32.057v15.565a.059.059.0 01-.049.059L.069 50.25A.06.06.0 01.005 50.167l14.987-33.47a.06.06.0 01.114.025z" fill="white"></path>
                                    <path d="m17.631 23.087v24.6a.06.06.0 00.053.059l22.449 2.507a.06.06.0 00.061-.084L17.745.032a.06.06.0 00-.114.024z" fill="white"></path>
                                    <path d="m39.961 52.548-24.833 7.45a.062.062.0 01-.043.0L.079 52.548a.059.059.0 01.026-.113h39.839a.06.06.0 01.017.113z" fill="white"></path>
                                </svg>
                            </List.Icon>
                        </Grid.Column>
                        <Grid.Column width={14}>
                            <List inverted>
                                <List.Item>
                                    <List.Header as='h3'>Istio</List.Header>
                                    <List.Description as='a' href="https://istio.io/latest/docs/setup/getting-started/">https://istio.io/latest/docs/setup/getting-started/</List.Description>
                                </List.Item>
                            </List>
                        </Grid.Column>
                    </Grid.Row>
                </Grid>
            </Segment>
        </Container>
    );
}