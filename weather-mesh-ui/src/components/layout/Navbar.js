import React, {useContext} from 'react'
import {Link} from 'react-router-dom'
import {Icon, Menu, MenuHeader, MenuItem, MenuMenu} from 'semantic-ui-react'
import AuthContext from '../auth/AuthContext'

function Navbar() {
    const {getLogin, isUserAuthenticated, userLogout} = useContext(AuthContext)

    const handleLogout = () => {
        userLogout()
    }

    const loginMenuStyle = () => {
        return isUserAuthenticated ? {"display": "none"} : {"display": "block"}
    }

    const logoutMenuStyle = () => {
        return isUserAuthenticated ? {"display": "block"} : {"display": "none"}
    }

    const adminPageStyle = () => {
        const user = getLogin()
        return user && user.role === 'ADMIN' ? {"display": "block"} : {"display": "none"}
    }

    const userPageStyle = () => {
        const user = getLogin()
        return user && user.role === 'USER' ? {"display": "block"} : {"display": "none"}
    }

    return (
        <Menu vertical size='massive' color={"black"} fluid inverted>
            <MenuItem>
                <MenuHeader>Авторизация</MenuHeader>
                <MenuMenu>
                    <Menu.Item as={Link} to="/login" style={loginMenuStyle()}>
                        <Icon name='sign in'/>
                        Войти в систему
                    </Menu.Item>
                    <Menu.Item as={Link} to="/" style={logoutMenuStyle()} onClick={handleLogout}>
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
                    <Menu.Item as={Link} exact='true' to="/">
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
                        <Icon name='info'/>
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
