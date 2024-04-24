import React from 'react'
import {Container, Segment} from 'semantic-ui-react'

export default function Links() {
    return (
        <Container>
            <Segment inverted>
                <h3 style={{textAlign: 'center'}}> Полезные ссылки </h3>
            </Segment>

            <Segment inverted>
                Github
                <br/>
                Telegram
                <br/>
                Istio docs
            </Segment>
        </Container>
    );
}