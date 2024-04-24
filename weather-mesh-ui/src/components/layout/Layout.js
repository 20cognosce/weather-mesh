import React, {Component} from 'react'
import {Container, Grid} from 'semantic-ui-react'
import LayoutNavbar from "./LayoutNavbar";
import LayoutHeader from "./LayoutHeader";

class Layout extends Component {
    render() {
        const {page} = this.props;
        return (
            <Container>
                <LayoutHeader/>
                <Grid stackable color={"black"} inverted>
                    <Grid.Row>
                        <Grid.Column width={5}>
                            <Container>
                                <LayoutNavbar/>
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

export default Layout