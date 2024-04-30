import React, {useContext, useEffect, useState} from 'react'
import {Button, Container, Modal, ModalActions, ModalContent, ModalHeader, Segment, Table} from 'semantic-ui-react'
import {circuitBreakerApi} from "../../api/CircuitBreakerApi";
import {handleLogError} from "../../util/ErrorHandler";
import AuthContext from "../../auth/AuthContext";
import {Link} from "react-router-dom";
import Pagination from "./Pagination";

export default function History() {
    const {getRole, getToken} = useContext(AuthContext)
    const [requests, setRequests] = useState([]);
    const [getCurrentEntities, render] = Pagination(requests, 10)

    const fetchData = async () => {
        try {
            const circuitBreakerResponse = await circuitBreakerApi.getRequests(getToken());
            setRequests(circuitBreakerResponse.data);
        } catch (error) {
            handleLogError(error);
        }
    }

    useEffect(() => {
        fetchData()
    }, []);

    if ('ADMIN' !== getRole()) {
        return (
            <div>
                <Modal open>
                    <ModalHeader>Не пройдена проверка соответствия прав</ModalHeader>
                    <ModalContent>
                        Только пользователи с ролью <b>ADMIN</b> могут просматривать историю обработанных запросов
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
                    <h3 style={{textAlign: 'center'}}> Обработанные запросы </h3>
                </Segment>

                <Table celled inverted style={{fontWeight: 'bolder', background: '#1B1C1D'}}>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>ID</Table.HeaderCell>
                            <Table.HeaderCell>From</Table.HeaderCell>
                            <Table.HeaderCell>To</Table.HeaderCell>
                            <Table.HeaderCell>Status</Table.HeaderCell>
                            <Table.HeaderCell>Timestamp</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {getCurrentEntities.map(request => (
                            <Table.Row key={request.id}>
                                <Table.Cell>{request.id}</Table.Cell>
                                <Table.Cell>{request.requestFromSystem.name}</Table.Cell>
                                <Table.Cell>{request.requestToSystem.name}</Table.Cell>
                                <Table.Cell>{request.status}</Table.Cell>
                                <Table.Cell>{request.timestamp.replace('T', ' ')}</Table.Cell>
                            </Table.Row>
                        ))}
                    </Table.Body>
                    {render}
                </Table>
            </Container>
        );
    }
}
