<template>
    <v-content>
        <v-dialog v-model="inputVal" persistent max-width="800px">
            <v-card color="basil">
                <v-card-title class="text-center justify-center py-6">
                    <h1 class="font-weight-bold display-1 basil--text">Настройки</h1>
                </v-card-title>

                <v-tabs
                        v-model="tab"
                        background-color="transparent"
                        color="basil"
                        grow
                >
                    <v-tab
                            :key="'Личная информация'"
                    >
                        Личная информация
              </v-tab>
                    <v-tab
                            :key="'Аккаунт'"
                    >
                        Аккаунт
              </v-tab>
                </v-tabs>

                <v-tabs-items v-model="tab">
                    <v-tab-item
                            :key="'Личная информация'"
                    >
                        <v-card
                                color="basil"
                                flat
                        >
                            <v-card-text>
                                <v-row
                                        align="center"
                                        justify="center"
                                >
                                    <v-col
                                            cols="12"
                                            sm="8"
                                            md="10"
                                    >
                                        <v-text-field
                                                v-model="firstname"
                                                prepend-icon="edit"
                                                color="teal"
                                                label="Имя"
                                        ></v-text-field>

                                        <v-text-field
                                                v-model="lastname"
                                                prepend-icon="edit"
                                                color="teal"
                                                label="Фамилия"
                                                persistent-hint
                                        ></v-text-field>

                                        <v-text-field
                                                v-model="middlename"
                                                prepend-icon="edit"
                                                color="teal"
                                                label="Отчество"
                                                persistent-hint
                                        ></v-text-field>

                                        <v-text-field
                                                v-model="age"
                                                prepend-icon="edit"
                                                color="teal"
                                                label="Возраст"
                                                persistent-hint
                                        ></v-text-field>
                                    </v-col>
                                </v-row>
                            </v-card-text>
                        </v-card>
                    </v-tab-item>
                    <v-tab-item
                            :key="'Аккаунт'"
                    >
                        <v-card
                                color="basil"
                                flat
                        >
                            <v-card-text>
                                <v-row
                                        align="center"
                                        justify="center"
                                >
                                    <v-col
                                            cols="12"
                                            sm="8"
                                            md="10"
                                    >

                                        <v-text-field
                                                v-model="password"
                                                prepend-icon="lock"
                                                type="password"
                                                label="Изменить пароль"
                                        ></v-text-field>

                                    </v-col>
                                </v-row>
                            </v-card-text>
                        </v-card>
                    </v-tab-item>
                </v-tabs-items>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="red darken-1" text @click="closeSettings()">Закрыть</v-btn>
                    <v-btn class="mr-7" color="green darken-1" text @click="saveSettings()">Сохранить</v-btn>
                    <v-overlay :value="overlay">
                        <v-progress-circular indeterminate size="64"></v-progress-circular>
                    </v-overlay>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-dialog v-model="showSettingsMessage" persistent max-width="400px">
            <v-card>
                <v-img
                        class="white--text primary darken-3"
                        height="55px"
                >
                    <v-card-title class="justify-center">
                        <span class="headline">{{ settingsTitle }}</span>
                    </v-card-title>
                </v-img>
                <v-card-text>
                    <v-container>
                        <div>
                            <p class="text-center">
                                {{ settingsMessage }}
                        </p>
                        </div>
                    </v-container>
                </v-card-text>
                <v-card-actions class="justify-center">
                    <v-btn color="primary darken-1" rounded @click="showSettingsMessage = false">Закрыть</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-content>
</template>

<script>
    import { mapState, mapActions } from 'vuex'

    export default {
        name: 'SettingsComponent',
        data: () => ({
            tab: null,
            items: [
                'Личная информация', 'Аккаунт'
            ],
            text: 'ааааааааааааааа',
            firstname: '',
            lastname: '',
            middlename: '',
            age: '',
            password: '',
            overlay: false,
            showSettingsMessage: false,
            settingsTitle: 'Ошибка',
            settingsMessage: 'Данные не изменены!'
        }),
        methods: {
            ...mapActions(['saveSettingsAction', 'setSettings']),
            saveSettings() {
                const user = {
                    id: this.currentUser.id,
                    firstname: this.firstname,
                    lastname: this.lastname,
                    middlename: this.middlename,
                    password: this.password,
                    age: this.age,
                }
                this.saveSettingsAction(user)
                this.password = ''
                this.overlay = !this.overlay
            },
            closeSettings() {
                this.firstname = this.currentUser.firstname
                this.lastname = this.currentUser.lastname
                this.middlename = this.currentUser.middlename
                this.age = this.currentUser.age
                this.setSettings(false)
            },
            checkSettings() {
                if (this.isSettings) {
                    this.firstname = this.currentUser.firstname
                    this.lastname = this.currentUser.lastname
                    this.middlename = this.currentUser.middlename
                    this.age = this.currentUser.age
                    return true
                }
                return false
            }
        },
        computed: {
            ...mapState(['currentUser', 'statusSettings', 'isSettings']),
            inputVal: {
                get() {
                    return this.checkSettings()
                }
            }
        },
        watch: {
            overlay (val) {
                val && setTimeout(() => {
                    if (this.statusSettings === 'success') {
                        this.showSettingsMessage = true
                        this.settingsTitle = 'Успешно'
                        this.settingsMessage = 'Данные изменены!'
                    }
                    this.overlay = false
                }, 1000)
            },
        },
    }
</script>

<style>
    .basil {
        background-color: #FFFBE6 !important;
    }
    .basil--text {
        color: #2971d6 !important;
    }
</style>