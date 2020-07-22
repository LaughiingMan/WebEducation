<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-navigation-drawer
            id="app-drawer"
            src="/img/navigation-drawer.jpg"
            app
            color="grey darken-2"
            dark
            floating
            mobile-break-point="991"
            persistent
            width="350"
    >
        <template v-slot:img="attrs">
            <v-img
                    v-bind="attrs"
                    gradient="to top, rgba(0, 0, 0, .7), rgba(0, 0, 0, .7)"
            />
        </template>

        <v-list-item two-line>
            <v-list-item-title class="title text-center">
                Профиль
              </v-list-item-title>
        </v-list-item>

        <v-divider class="mx-5 mb-5 grey" />

        <v-list nav>
            <div />

            <v-list-item
                    v-for="(link, i) in links"
                    :key="i"
                    :to="link.to"
                    active-class="primary white--text"
            >
                <v-list-item-action>
                    <v-icon>{{ link.icon }}</v-icon>
                </v-list-item-action>

                <v-list-item-title v-text="link.text" />
            </v-list-item>
        </v-list>

        <template v-slot:append>
            <v-row justify="center">
                <v-btn text icon class="ma-12" @click="home">
                    <v-icon x-large>home</v-icon>
                </v-btn>
            </v-row>
        </template>
    </v-navigation-drawer>
</template>

<script>
    import { mapState } from 'vuex'

    export default {
        name: 'AppBar',
        data () {
            return {
                links: [
                    {
                        to: '/profile/users',
                        icon: 'person',
                        text: 'Пользователи'
                    },
                    {
                        to: '/profile/statistics',
                        icon: 'equalizer',
                        text: 'Статистика'
                    },
                    {
                        to: '/profile/test',
                        icon: 'list',
                        text: 'Тестирование'
                    }
                ]
            }
        },
        methods: {
            home() {
                this.$router.push("/")
            }
        },
        created() {
            if (this.currentUser.roles.includes('ADMIN')) {
                this.links.push({
                    to: '/profile/build',
                    icon: 'brightness_low',
                    text: 'Конструктор тестов'
                })
            }
        },
        computed: {
            ...mapState(['currentUser'])
        }
    }
</script>

<style>

</style>