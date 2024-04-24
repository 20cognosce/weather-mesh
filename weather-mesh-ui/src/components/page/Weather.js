import React, {useContext, useState} from 'react'
import {Link} from 'react-router-dom'
import {
    Button,
    Container,
    Dropdown,
    Form,
    Modal,
    ModalActions,
    ModalContent,
    ModalHeader,
    Segment
} from 'semantic-ui-react'
import AuthContext from '../auth/AuthContext'
import {handleLogError} from "../util/ErrorHandler";
import {weatherApi} from "../api/WeatherApi";

export default function Weather() {
    const {getRole, getToken} = useContext(AuthContext)
    const [formData, setFormData] = useState({condition: '', month: '', city: ''});
    const [response, setResponse] = useState(null);

    const handleInputChange = (e, {name, value}) => {
        setFormData({...formData, [name]: value});
    }

    const handleSubmit = async () => {
        try {
            const weatherInfoResponse = await weatherApi.postInfo(getToken(), formData);
            const info = weatherInfoResponse.data;
            setResponse({
                value: info.value,
                description: info.description
            });
        } catch (error) {
            handleLogError(error);
        }
    }

    const russianOptions = {
        condition: {
            Temperature: "Температура",
            Precipitation: "Осадки",
            Sunshine: "Солнечность"
        },
        month: {
            January: "Январь",
            February: "Февраль",
            March: "Март",
            April: "Апрель",
            May: "Май",
            June: "Июнь",
            July: "Июль",
            August: "Август",
            September: "Сентябрь",
            October: "Октябрь",
            November: "Ноябрь",
            December: "Декабрь"
        },
        city: {
            Moscow: "Москва",
            SaintPetersburg: "Санкт-Петербург",
            Kazan: "Казань",
            Novorossiysk: "Новороссийск",
            Vladivostok: "Владивосток",
            Omsk: "Омск"
        }
    };

    const dropdownOptions = (option) => {
        return Object.keys(option).map(entry => ({
            key: entry,
            text: option[entry],
            value: entry
        }));
    };

    if ('USER' !== getRole()) {
        return (
            <div>
                <Modal open='true'>
                    <ModalHeader>Не пройдена проверка соответствия прав</ModalHeader>
                    <ModalContent>
                        Только пользователи с ролью <b>USER</b> могут выполнять поиск данных о погоде
                    </ModalContent>
                    <ModalActions>
                        <Button positive as={Link} to="/">
                            Ясно
                        </Button>
                    </ModalActions>
                </Modal>
            </div>
        )
    } else {
        return (
            <Container>
                <Segment inverted>
                    <h3 style={{textAlign: 'center'}}> Запрос данных о погоде </h3>
                </Segment>

                <Segment inverted>
                    <Form onSubmit={handleSubmit} style={{textAlign: 'center'}}>
                        <Form.Group widths='equal'>
                            <Form.Field>
                                <label>Условия погоды</label>
                                <Dropdown
                                    placeholder='Выберите условия'
                                    fluid
                                    selection
                                    options={dropdownOptions(russianOptions.condition)}
                                    name='condition'
                                    value={formData.condition}
                                    onChange={handleInputChange}
                                />
                            </Form.Field>
                            <Form.Field>
                                <label>Месяц</label>
                                <Dropdown
                                    placeholder='Выберите месяц'
                                    fluid
                                    selection
                                    options={dropdownOptions(russianOptions.month)}
                                    name='month'
                                    value={formData.month}
                                    onChange={handleInputChange}
                                />
                            </Form.Field>
                            <Form.Field>
                                <label>Город</label>
                                <Dropdown
                                    placeholder='Выберите город'
                                    fluid
                                    selection
                                    options={dropdownOptions(russianOptions.city)}
                                    name='city'
                                    value={formData.city}
                                    onChange={handleInputChange}
                                />
                            </Form.Field>
                        </Form.Group>
                        <br/>
                        <Button type='submit'
                                style={{fontWeight: 'bolder', background: '#FFFFFF'}}>Поиск данных!</Button>
                        <br/><br/>
                    </Form>
                    {response && (
                        <Segment>
                            <div>
                                <p><strong>Значение:</strong> {response.value}</p>
                                <p><strong>Описание:</strong> {response.description}</p>
                            </div>
                        </Segment>
                    )}
                </Segment>
            </Container>
        );
    }
}