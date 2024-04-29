import React from 'react'
import {Container, Grid, Segment} from 'semantic-ui-react'

export default function Author() {
    return (
        <Container>
            <Segment inverted>
                <h3 style={{textAlign: 'center'}}> Информация об авторе </h3>
            </Segment>

            <Segment inverted>
                <Grid columns={2} divided relaxed inverted>
                    <Grid.Row>
                        <Grid.Column width={3} verticalAlign='middle'>
                            <strong>Разработчик</strong>
                        </Grid.Column>
                        <Grid.Column width={13}>
                            Верт Дмитрий Андреевич
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column width={3} verticalAlign='middle'>
                            <strong>Курс</strong>
                        </Grid.Column>
                        <Grid.Column width={13}>
                            4-ый
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column width={3} verticalAlign='middle'>
                            <strong>Группа</strong>
                        </Grid.Column>
                        <Grid.Column width={13}>
                            ИКБО-24-20
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column width={3} verticalAlign='middle'>
                            <strong>Направление</strong>
                        </Grid.Column>
                        <Grid.Column width={13}>
                            09.03.04 «Программная инженерия»
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column width={3} verticalAlign='middle'>
                            <strong>Профиль</strong>
                        </Grid.Column>
                        <Grid.Column width={13}>
                            «Разработка программных продуктов и проектирование информационных систем»
                        </Grid.Column>
                    </Grid.Row>
                    <Grid.Row>
                        <Grid.Column width={3} verticalAlign='middle'>
                            <strong>ВУЗ</strong>
                        </Grid.Column>
                        <Grid.Column width={13}>
                            ФГБОУ ВО «МИРЭА — Российский технологический университет»
                        </Grid.Column>
                    </Grid.Row>
                </Grid>
            </Segment>
        </Container>
    );
}