import React, {useContext, useEffect, useState} from 'react'
import {
    Button,
    Container,
    Dropdown,
    Modal,
    ModalActions,
    ModalContent,
    ModalHeader,
    Segment,
    Table
} from 'semantic-ui-react'
import AuthContext from "../../auth/AuthContext";
import {Link, useLocation} from "react-router-dom";
import {circuitBreakerApi} from "../../api/CircuitBreakerApi";
import {handleLogError} from "../../util/ErrorHandler";

export default function PermissionsEdit() {
    const {getRole, getToken} = useContext(AuthContext)
    const location = useLocation();
    const [permission, setPermission] = useState({id: '', requestToSystem: '', requestFromSystem: '', status: ''});
    const [isLoading, setIsLoading] = useState(false);

    const fetchPermissionData = async () => {
        const params = new URLSearchParams(location.search);
        const id = params.get('id');
        try {
            const response = await circuitBreakerApi.getPermissionById(getToken(), id);
            setPermission(response.data[0]);
        } catch (error) {
            handleLogError(error)
        }
    };

    const handleInputChange = (e, {name, value}) => {
        setPermission({...permission, [name]: value});
    }

    const handleSubmit = async () => {
        try {
            setIsLoading(true)
            await circuitBreakerApi.patchPermissionById(getToken(), permission);
            setTimeout(
                function () {
                    setIsLoading(false)
                }, 1000);
        } catch (error) {
            handleLogError(error);
        }
    }

    useEffect(() => {
        fetchPermissionData();
    }, []);

    if ('ADMIN' !== getRole()) {
        return (
            <div>
                <Modal open>
                    <ModalHeader>Не пройдена проверка соответствия прав</ModalHeader>
                    <ModalContent>
                        Только пользователи с ролью <b>ADMIN</b> могут редактировать доступы
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
                    <h3 style={{textAlign: 'center'}}> Редактирование доступа </h3>
                </Segment>

                <Table celled inverted style={{fontWeight: 'bolder', background: '#1B1C1D'}}>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>ID</Table.HeaderCell>
                            <Table.HeaderCell>From</Table.HeaderCell>
                            <Table.HeaderCell>To</Table.HeaderCell>
                            <Table.HeaderCell>Status</Table.HeaderCell>
                            <Table.HeaderCell style={{textAlign: 'center'}}>Submit</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        <Table.Row key={permission.id}>
                            <Table.Cell>{permission.id}</Table.Cell>
                            <Table.Cell>{permission.requestFromSystem}</Table.Cell>
                            <Table.Cell>{permission.requestToSystem}</Table.Cell>
                            <Table.Cell>
                                <Dropdown
                                    placeholder={permission.status}
                                    fluid
                                    selection
                                    options={[
                                        {text: 'ALLOWED', value: 'ALLOWED'},
                                        {text: 'FORBIDDEN', value: 'FORBIDDEN'},
                                    ]}
                                    name='status'
                                    value={permission.status}
                                    onChange={handleInputChange}
                                />
                            </Table.Cell>
                            <Table.Cell style={{display: 'flex', justifyContent: 'center'}}>
                                <Button
                                    style={{margin: 0}}
                                    circular
                                    color='blue'
                                    size='medium'
                                    icon='check'
                                    loading={isLoading}
                                    onClick={handleSubmit}
                                />
                            </Table.Cell>
                        </Table.Row>
                    </Table.Body>
                </Table>
            </Container>
        );
    }
}

