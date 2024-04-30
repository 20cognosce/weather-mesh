import React, {useContext, useEffect, useState} from 'react'
import {Button, Container, Modal, ModalActions, ModalContent, ModalHeader, Segment, Table} from 'semantic-ui-react'
import {circuitBreakerApi} from "../../api/CircuitBreakerApi";
import {handleLogError} from "../../util/ErrorHandler";
import AuthContext from "../../auth/AuthContext";
import {Link} from "react-router-dom";

export default function Systems() {
    const {getRole, getToken} = useContext(AuthContext)
    const [systems, setSystems] = useState([]);

    const fetchData = async () => {
        try {
            const circuitBreakerSystemsResponse = await circuitBreakerApi.getSystems(getToken());
            setSystems(circuitBreakerSystemsResponse.data);
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
                        Только пользователи с ролью <b>ADMIN</b> могут просматривать зарегистрированные системы
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
                    <h3 style={{textAlign: 'center'}}> Зарегистрированные системы </h3>
                </Segment>

                <Table celled inverted style={{fontWeight: 'bolder', background: '#1B1C1D'}}>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>ID</Table.HeaderCell>
                            <Table.HeaderCell>Name</Table.HeaderCell>
                            <Table.HeaderCell>Registration Time</Table.HeaderCell>
                            <Table.HeaderCell>Registration Type</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {systems.map(system => (
                            <Table.Row key={system.id}>
                                <Table.Cell>{system.id}</Table.Cell>
                                <Table.Cell>{system.name}</Table.Cell>
                                <Table.Cell>{system.registrationTime.replace('T', ' ')}</Table.Cell>
                                <Table.Cell>{system.registrationType}</Table.Cell>
                            </Table.Row>
                        ))}
                    </Table.Body>
                </Table>
            </Container>
        );
    }
}
