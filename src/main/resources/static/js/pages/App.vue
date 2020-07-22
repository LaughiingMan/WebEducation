<template>
    <v-app light>
        <v-content v-if="$route.path === '/'">
            <section>
                <v-parallax src="/img/start_view.jpg" :height="this.getWindowHeight()">
                    <v-layout
                            column
                    >
                        <v-app-bar
                                fixed
                                flat
                                :color="dynamic"
                                v-scroll="onScroll"
                        >
                            <div class="flex-grow-1"></div>
                            <v-btn
                                    class="white--text"
                                    text
                                    rounded
                                    @click="downloadManual()"
                            >Руководство пользователя
                            <v-icon right>book</v-icon>
                            </v-btn>
                            <v-btn
                                    class="white--text"
                                    text
                                    rounded
                                    @click="setSettings(true)"
                            >Настройки
                            <v-icon right>settings_applications</v-icon>
                            </v-btn>
                            <v-btn
                                    class="white--text"
                                    text
                                    rounded
                                    @click="logout()"
                            >Выход
                            <v-icon right>exit_to_app</v-icon>
                            </v-btn>
                        </v-app-bar>
                    </v-layout>
                    <v-row
                        class="mb-6"
                        no-gutters
                        >
                        <v-col
                                class="text-center justify-center"
                                md="6"
                                offset-md="3"
                        >
                            <h1 class="white--text display-2">Информационный ресурс</h1>
                            <h3 class="mt-5 display-5 text-center">
                                Данный веб - ресурс используются для связи студентов с психологом и дает
                                возможность психологам проводить тестирования на испытуемых, вести статистику,
                                а так же создавать тесты при помощи конструктора.
                            </h3>
                            <v-btn class="mt-5 primary" @click="showProfile">Начать</v-btn>
                        </v-col>
                    </v-row>
                </v-parallax>
            </section>

            <section>
                <v-layout
                        column
                        wrap
                        class="my-12"
                        align-center
                >
                    <v-flex xs12 sm4 class="my-4">
                        <div class="text-center">
                            <h1 class="headline">Главные особенности!</h1>
                            <span class="subheading">Воспользуйся</span>
                        </div>
                    </v-flex>
                    <v-flex xs12>
                        <v-container grid-list-xl>
                            <v-layout row wrap align-center>
                                <v-flex xs12 md4>
                                    <v-card flat class="transparent">
                                        <v-card-text class="text-center">
                                            <v-icon x-large class="blue--text text--lighten-2">hot_tub</v-icon>
                                        </v-card-text>
                                        <v-card-title primary-title class="layout justify-center">
                                            <div class="headline text-center">
                                                Тестирование
                                            </div>
                                        </v-card-title>
                                        <v-card-text class="mt-5 text-justify">
                                            Тестирование основано на системе заранее отобранных и проверенных с точки
                                            зрения их валидности и надежности вопросов, по ответам испытуемых, на которые
                                            определенно можно судить об их психологических качествах.
                                        </v-card-text>
                                    </v-card>
                                </v-flex>
                                <v-flex xs12 md4>
                                    <v-card flat class="transparent">
                                        <v-card-text class="text-center">
                                            <v-icon x-large class="blue--text text--lighten-2">build</v-icon>
                                        </v-card-text>
                                        <v-card-title primary-title class="layout justify-center">
                                            <div class="headline text-center">
                                                Конструктор
                                            </div>
                                        </v-card-title>
                                        <v-card-text class="mt-5 text-justify">
                                            Конструктор предоставляет психологам мощный и простой в использовании
                                            инструмент для создания тестов.
                                            Можно создавать, редактировать и удалять тесты, а также конструктор
                                            оснащён хранилищем результатов.
                                        </v-card-text>
                                    </v-card>
                                </v-flex>
                                <v-flex xs12 md4>
                                    <v-card flat class="transparent">
                                        <v-card-text class="text-center">
                                            <v-icon x-large class="blue--text text--lighten-2">timeline</v-icon>
                                        </v-card-text>
                                        <v-card-title primary-title class="layout justify-center">
                                            <div class="headline text-center">
                                                Статистика
                                            </div>
                                        </v-card-title>
                                        <v-card-text class="mt-5 text-justify">
                                            Статистика позволяет собирать всю необходимую информацию, анализировать данные
                                            и определять наличие закономерностей на основе собранных данных.
                                        </v-card-text>
                                    </v-card>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-flex>
                </v-layout>
            </section>
            <v-footer color="primary" padless>
                <v-layout row wrap align-center>
                    <v-flex xs12>
                        <v-card
                                flat
                                tile
                                class="primary white--text text-center"
                        >
                            <v-card-text class="white--text pt-5">
                                Информационный ресурс
                            </v-card-text>

                            <v-divider class="grey darken-3"></v-divider>

                            <v-card-text class="white--text">
                                <strong>{{ new Date().getFullYear() }} — Петров Евгений</strong>
                            </v-card-text>
                        </v-card>
                    </v-flex>
                </v-layout>
            </v-footer>
            <settings-component></settings-component>
        </v-content>
        <v-content>
            <transition>
                <router-view name="profile"></router-view>
                <router-view name="authorization"></router-view>
            </transition>
        </v-content>
    </v-app>
</template>

<script>
    import store from '../store/store'
    import { mapGetters, mapState, mapActions } from 'vuex'
    import SettingsComponent from '../components/SettingsComponent.vue'

    export default {
        components: {SettingsComponent},
        data() {
          return {
              dynamic: 'transparent',
          }
        },
        /*created() {
            store.dispatch('findUsersAction')
            store.dispatch('findTestsAction')
        },*/
        methods: {
            ...mapGetters(['getWindowHeight']),
            ...mapActions(['setSettings']),
            onScroll() {
                if (window.pageYOffset > 500) {
                    this.dynamic = 'primary'
                } else {
                    this.dynamic = 'transparent'
                }
            },
            showProfile() {
                this.$router.push('/profile/users')
            },
            downloadManual() {
                window.location.href = '/menu/manual'
            },
            logout() {
                window.$cookies.remove('jwt.token')
                window.$cookies.remove('roles')
                this.$router.push('/authorization')
            }
        },
        computed: {
            ...mapState(['isSettings'])
        }
    }
</script>

<style>

</style>