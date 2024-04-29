import React, {useContext, useEffect, useState} from 'react'
import {Button, Container, Modal, ModalActions, ModalContent, ModalHeader, Segment, Table} from 'semantic-ui-react'
import {circuitBreakerApi} from "../../api/CircuitBreakerApi";
import {handleLogError} from "../../util/ErrorHandler";
import AuthContext from "../../auth/AuthContext";
import {Link} from "react-router-dom";

export default function Permissions() {
    const {getRole, getToken} = useContext(AuthContext)
    const [permissions, setPermissions] = useState([]);

    const fetchData = async () => {
        try {
            const circuitBreakerResponse = await circuitBreakerApi.getPermissions(getToken());
            setPermissions(circuitBreakerResponse.data);
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
                <Modal open='true'>
                    <ModalHeader>Не пройдена проверка соответствия прав</ModalHeader>
                    <ModalContent>
                        Только пользователи с ролью <b>ADMIN</b> могут управлять доступами
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
                    <h3 style={{textAlign: 'center'}}> Состояние системных доступов </h3>
                </Segment>

                <Table celled inverted style={{fontWeight: 'bolder', background: '#1B1C1D'}}>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>ID</Table.HeaderCell>
                            <Table.HeaderCell>From</Table.HeaderCell>
                            <Table.HeaderCell>To</Table.HeaderCell>
                            <Table.HeaderCell>Status</Table.HeaderCell>
                            <Table.HeaderCell style={{textAlign: 'center'}}>Edit</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {permissions.map(permission => (
                            <Table.Row key={permission.id}>
                                <Table.Cell>{permission.id}</Table.Cell>
                                <Table.Cell>{permission.requestFromSystem}</Table.Cell>
                                <Table.Cell>{permission.requestToSystem}</Table.Cell>
                                <Table.Cell>{permission.status}</Table.Cell>
                                <Table.Cell style={{display: 'flex', justifyContent: 'center'}}>
                                    <Button
                                        style={{margin: 0}}
                                        circular
                                        color='blue'
                                        size='medium'
                                        icon='cog'
                                        as={Link} to={`ui/permissions/edit?id=${permission.id}`}
                                    />
                                </Table.Cell>
                            </Table.Row>
                        ))}
                    </Table.Body>
                </Table>
            </Container>
        );
    }
}
