import React, {useState} from 'react'
import {Container, Image, Modal, Segment} from 'semantic-ui-react'
import {Link} from "react-router-dom";

export default function About() {
    const [fullscreen, setFullscreen] = useState(false);

    const toggleFullscreen = () => {
        setFullscreen(!fullscreen);
    };

    return (
        <Container>
            <Segment inverted>
                <h3 style={{textAlign: 'center'}}> Информация о системе </h3>
            </Segment>

            <Segment inverted style={{lineHeight: '1.5'}}>
                <p>
                    <strong>Weather Mesh</strong> (приложение погоды) — демонстративный распределенный веб-сервис,
                    реализующий в своей архитектуре интеграционный паттерн <strong>Service Mesh</strong> посредством
                    фреймворка <strong>Istio</strong>.
                </p>
            </Segment>

            <Segment onClick={toggleFullscreen} style={{
                padding: '2px',
                margin: 'auto',
                width: '66%',
                borderRadius: '2%',
                cursor: 'pointer'
            }}>
                <Image src="/AppArchitecture.jpg"/>
            </Segment>

            <Modal open={fullscreen} onClose={toggleFullscreen} closeIcon>
                <Modal.Content image style={{display: 'flex', justifyContent: 'center', padding: '5px'}}>
                    <Image src="/AppArchitecture.jpg" wrapped/>
                </Modal.Content>
            </Modal>

            <Segment inverted style={{textAlign: 'justify', lineHeight: '1.5'}}>
                <p>
                    <strong>Микросервис погоды: </strong>
                    отвечает за взаимодействие с пользователем, желающим получить сведения о погоде в определенную
                    дату в прошлом,
                    с последующим обращением за этой информацией к сервису справочных данных.
                </p>
                <p>
                    <strong>Микросервис справочных данных: </strong>
                    хранит в своей базе данных сведения о погоде в определенную дату и предоставляет их с
                    применением какой-либо выборки.
                    Все данные взяты с <Link to={"https://meteoinfo.ru/"}>сайта Гидрометцентра России</Link>.
                </p>
                <p>
                    <strong>Микросервис управления трафиком (Circuit Breaker): </strong>
                    прокси-сервис, который перехватывает запрос от сервиса погоды и либо пропускает его дальше
                    до сервиса справочных данных, либо сразу прерывает их сетевое взаимодействие с заготовленным
                    системным ответом.
                </p>
                <p>
                    <strong>Микросервис авторизации: </strong>
                    обеспечивает выдачу JWT токенов и авторизует по ним как пользователей веб-приложения, так и
                    другие сервисы.
                </p>
            </Segment>
        </Container>
    );
}