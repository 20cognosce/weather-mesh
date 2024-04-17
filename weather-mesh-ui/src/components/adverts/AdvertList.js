import React from 'react'
import {Form, Grid, Header, Icon, Image, Input, Item, Segment} from 'semantic-ui-react'

function AdvertList({
                        adverts,
                        advertTitleToFind,
                        isAdvertsLoading,
                        handleInputChange,
                        handleFindAdverts,
                        handleGetImageUrlById}) {
    let advertsList
    if (adverts.length === 0) {
        advertsList = <Item key='no-advert'>Пока нет объявлений</Item>
    } else {
        advertsList = adverts.map(advert => {
            let addressFromDto = Object.values(advert.address)
            addressFromDto.shift() //removes id
            const address = [];

            for (const item of addressFromDto) {
                if (item !== '') {
                    address.push(item);
                }
            }

            return (
                <Item key={advert.id}>
                    { (advert.images.length > 0) &&
                        <Image src={handleGetImageUrlById(advert.images[0].id)} size='large' bordered rounded/>
                    }
                    <Item.Content>
                        <Item.Header>{advert.title}</Item.Header>
                        <Item.Meta>#{advert.id}</Item.Meta>
                        <Item.Meta>{address.join(", ")}</Item.Meta>
                        <Item.Description>
                            {advert.description}
                        </Item.Description>
                    </Item.Content>
                </Item>
            )
        })
    }

    return (
        <Segment loading={isAdvertsLoading} color='blue'>
            <Grid stackable>
                <Grid.Row columns='2'>
                    <Grid.Column floated='left'>
                        <Header as='h1'>
                            <Icon name='newspaper outline'/>
                            <Header.Content>Объявления</Header.Content>
                        </Header>
                    </Grid.Column>

                    <Grid.Column floated='right' width={4}>
                        <Form onSubmit={handleFindAdverts}>
                            <Input
                                fluid
                                action={{icon: 'search'}}
                                name='advertTitleToFind'
                                placeholder='Поиск по названию'
                                value={advertTitleToFind}
                                onChange={handleInputChange}
                            />
                        </Form>
                    </Grid.Column>
                </Grid.Row>
            </Grid>

            <Item.Group divided unstackable relaxed link>
                {advertsList}
            </Item.Group>
        </Segment>
    )
}

export default AdvertList