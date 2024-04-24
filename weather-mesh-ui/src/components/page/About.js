import React from 'react'
import {Container, Segment} from 'semantic-ui-react'

export default function About() {
    return (
        <Container>
            <Segment inverted>
                <h3 style={{textAlign: 'center'}}> Информация о системе </h3>
            </Segment>

            <Segment inverted>
                Weather Mesh —
            </Segment>
        </Container>
    );
}