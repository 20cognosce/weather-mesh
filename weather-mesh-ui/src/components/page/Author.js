import React from 'react'
import {Container, Segment} from 'semantic-ui-react'

export default function Author() {
    return (
        <Container>
            <Segment inverted>
                <h3 style={{textAlign: 'center'}}> Информация об авторе </h3>
            </Segment>

            <Segment inverted>
                Верт Дмитрий Андреевич — разработчик проекта
                <br/>
                <br/>
                Студент 4-ого курса, группы ИКБО-24-20, направления «Программная инженерия», профиля «Разработка программных продуктов и проектирование информационных систем»
                <br/>
                <br/>
                ФГБУ ВО «МИРЭА — Российский технологический университет»
            </Segment>
        </Container>
    );
}