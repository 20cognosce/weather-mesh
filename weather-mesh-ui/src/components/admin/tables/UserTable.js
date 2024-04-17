import React from 'react'
import {Form, Button, Input, Table} from 'semantic-ui-react'

function UserTable({
                       handleInputChange,
                       users,
                       userEmailToFind,
                       handleFindUsers,
                       handleDeleteUser,
                       handleActivateUser}) {
    let userList
    if (users.length === 0) {
        userList = (
            <Table.Row key='no-user'>
                <Table.Cell collapsing textAlign='center' colSpan='6'>Пока нет пользователей</Table.Cell>
            </Table.Row>
        )
    } else {
        userList = users.map(user => {
            return (
                <Table.Row key={user.id}>
                    <Table.Cell collapsing>
                        <Button
                            circular
                            color='red'
                            size='small'
                            icon='ban'
                            disabled={user.role === 'ADMIN'}
                            onClick={() => handleDeleteUser(user.id)}
                        />
                    </Table.Cell>
                    <Table.Cell collapsing>
                        <Button
                            circular
                            color='green'
                            size='small'
                            icon='checkmark'
                            disabled={user.role === 'ADMIN'}
                            onClick={() => handleActivateUser(user.id)}
                        />
                    </Table.Cell>
                    <Table.Cell>{user.id}</Table.Cell>
                    <Table.Cell>{user.firstName}</Table.Cell>
                    <Table.Cell>{user.lastName}</Table.Cell>
                    <Table.Cell>{user.email}</Table.Cell>
                    <Table.Cell>{user.role}</Table.Cell>
                    <Table.Cell>{user.status}</Table.Cell>
                </Table.Row>
            )
        })
    }

    return (
        <>
            <Form onSubmit={handleFindUsers}>
                <Input
                    action={{icon: 'search'}}
                    name='userEmailToFind'
                    placeholder='Поиск по почте'
                    value={userEmailToFind}
                    onChange={handleInputChange}
                />
            </Form>
            <Table compact striped selectable>
                <Table.Header>
                    <Table.Row>
                        <Table.HeaderCell width={1}/>
                        <Table.HeaderCell width={1}/>
                        <Table.HeaderCell width={1}>ID</Table.HeaderCell>
                        <Table.HeaderCell width={2}>First Name</Table.HeaderCell>
                        <Table.HeaderCell width={3}>Last Name</Table.HeaderCell>
                        <Table.HeaderCell width={3}>Email</Table.HeaderCell>
                        <Table.HeaderCell width={2}>Role</Table.HeaderCell>
                        <Table.HeaderCell width={2}>Status</Table.HeaderCell>
                    </Table.Row>
                </Table.Header>
                <Table.Body>
                    {userList}
                </Table.Body>
            </Table>
        </>
    )
}

export default UserTable