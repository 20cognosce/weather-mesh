import React from 'react'
import {Link} from 'react-router-dom'
import {Icon, Menu, MenuHeader, MenuItem, MenuMenu} from 'semantic-ui-react'
import {useAuth} from '../auth/AuthContext'

function Navbar() {
    const {getUser, userIsAuthenticated, userLogout} = useAuth()

    const logout = () => {
        userLogout()
    }

    const enterMenuStyle = () => {
        return userIsAuthenticated() ? {"display": "none"} : {"display": "block"}
    }

    const logoutMenuStyle = () => {
        return userIsAuthenticated() ? {"display": "block"} : {"display": "none"}
    }

    const adminPageStyle = () => {
        const user = getUser()
        return user && user.role === 'ADMIN' ? {"display": "block"} : {"display": "none"}
    }

    const userPageStyle = () => {
        const user = getUser()
        return user && user.role === 'USER' ? {"display": "block"} : {"display": "none"}
    }

    const getUserFirstName = () => {
        const user = getUser()
        return user ? user.firstName : ''
    }

    return (
        <Menu vertical size='massive' color={"black"} fluid inverted>
            <MenuItem>
                <MenuHeader>Авторизация</MenuHeader>
                <MenuMenu>
                    <Menu.Item as={Link} to="/login" style={enterMenuStyle()}>
                        <Icon name='sign in'/>
                        Войти в систему
                    </Menu.Item>
                    <Menu.Item as={Link} to="/" style={logoutMenuStyle()} onClick={logout}>
                        <Icon name='sign out'/>
                        Выйти из системы
                    </Menu.Item>
                </MenuMenu>
            </MenuItem>
            <MenuItem>
                <MenuHeader>Сервис погоды</MenuHeader>
                <MenuMenu>
                    <Menu.Item as={Link} to="/home">
                        <Icon name='search'/>
                        Поиск
                    </Menu.Item>
                </MenuMenu>
            </MenuItem>
            <MenuItem>
                <MenuHeader>Сервис управления трафиком</MenuHeader>
                <MenuMenu>
                    <Menu.Item as={Link} exact='true' to="/" >
                        <Icon name='unlock'/>
                        Управление доступами
                    </Menu.Item>

                    <Menu.Item as={Link} exact='true' to="/">
                        <Icon name='file alternate'/>
                        История запросов
                    </Menu.Item>
                    <Menu.Item as={Link} exact='true' to="/">
                        <Icon name='shutdown'/>
                        Системы
                    </Menu.Item>
                    <Menu.Item as={Link} exact='true' to="/">
                        <Icon name='shield'/>
                        Аудит
                    </Menu.Item>


                    {/*<Menu.Item as={Link} to="/admin" style={adminPageStyle()}>Администрирование</Menu.Item>*/}
                    {/*<Menu.Item as={Link} to="/adverts" style={userPageStyle()}>Объявления</Menu.Item>*/}
                    {/*<Menu.Item as={Link} to="/adverts/new" style={userPageStyle()}>Разместить свое объявление</Menu.Item>*/}
                </MenuMenu>
            </MenuItem>
            <MenuItem>
                <MenuHeader>Дополнительно</MenuHeader>
                <MenuMenu>
                    <Menu.Item>
                        <Icon name='settings'/>
                        О системе
                    </Menu.Item>
                    <Menu.Item>
                        <Icon name='id card'/>
                        Об авторе
                    </Menu.Item>
                    <Menu.Item>
                        <Icon name='globe'/>
                        Ссылки
                    </Menu.Item>
                </MenuMenu>
            </MenuItem>
        </Menu>
    )
}

export default Navbar
