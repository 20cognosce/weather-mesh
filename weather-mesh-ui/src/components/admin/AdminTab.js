import React from 'react'
import {Tab} from 'semantic-ui-react'
import UserTable from './tables/UserTable'
import AdvertTable from './tables/AdvertTable'

function AdminTab(props) {
    const {handleInputChange} = props
    const {
        isUsersLoading,
        users,
        userEmailToFind,
        handleFindUsers,
        handleDeleteUser,
        handleActivateUser
    } = props
    const {
        isAdvertsLoading,
        adverts,
        advertTitleToFind,
        handleFindAdverts,
        handleGetImageUrlById,
        handleDeleteAdvert
    } = props

    const panes = [
        {
            menuItem: {key: 'users', icon: 'users', content: 'Пользователи'},
            render: () => (
                <Tab.Pane loading={isUsersLoading}>
                    <UserTable
                        handleInputChange={handleInputChange}
                        users={users}
                        userEmailToFind={userEmailToFind}
                        handleFindUsers={handleFindUsers}
                        handleDeleteUser={handleDeleteUser}
                        handleActivateUser={handleActivateUser}
                    />
                </Tab.Pane>
            )
        },
        {
            menuItem: {key: 'adverts', icon: 'newspaper', content: 'Объявления'},
            render: () => (
                <Tab.Pane loading={isAdvertsLoading}>
                    <AdvertTable
                        handleInputChange={handleInputChange}
                        adverts={adverts}
                        advertTitleToFind={advertTitleToFind}
                        handleFindAdverts={handleFindAdverts}
                        handleGetImageUrlById={handleGetImageUrlById}
                        handleDeleteAdvert={handleDeleteAdvert}
                    />
                </Tab.Pane>
            )
        }
    ]

    return (
        <Tab menu={{attached: 'top'}} panes={panes}/>
    )
}

export default AdminTab