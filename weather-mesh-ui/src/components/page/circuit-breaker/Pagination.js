import React, {useState} from "react";
import {Icon, Menu, Table} from "semantic-ui-react";

export default function Pagination(entitiesArray, entitiesPerPage) {
    const [currentPage, setCurrentPage] = useState(0);
    const totalPages = Math.ceil(entitiesArray.length / entitiesPerPage);
    const startIndex = currentPage * entitiesPerPage;
    const endIndex = startIndex + entitiesPerPage;
    const currentEntities = entitiesArray.slice(startIndex, endIndex);

    const handleClick = (page) => setCurrentPage(page);

    function getCurrentEntities() {
        return currentEntities;
    }

    function render() {
        return (
            <Table.Footer>
                <Table.Row>
                    <Table.HeaderCell colSpan='8'>
                        <Menu floated='right' pagination inverted
                              style={{outline: '1px solid #FFFFFF99', background: '#1B1C1D'}}>
                            <Menu.Item as='a' icon
                                       onClick={() => handleClick(currentPage - 1)}
                                       disabled={currentPage === 0}>
                                <Icon name='chevron left'/>
                            </Menu.Item>
                            {[...Array(totalPages).keys()].map(pageNum => (
                                <Menu.Item as='a' key={pageNum + 1}
                                           active={pageNum === currentPage}
                                           onClick={() => handleClick(pageNum)}>
                                    {pageNum + 1}
                                </Menu.Item>
                            ))}
                            <Menu.Item as='a' icon
                                       onClick={() => handleClick(currentPage + 1)}
                                       disabled={currentPage === totalPages - 1}>
                                <Icon name='chevron right'/>
                            </Menu.Item>
                        </Menu>
                    </Table.HeaderCell>
                </Table.Row>
            </Table.Footer>
        )
    }

    return ([getCurrentEntities(), render()])
}
