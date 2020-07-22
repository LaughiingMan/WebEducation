<template>
    <v-app>
        <v-content>
            <v-container
                    class="fill-height login-background"
                    fluid
            >
                <v-row
                        align="center"
                        justify="center"
                >
                    <v-col
                            cols="12"
                            sm="8"
                            md="4"
                    >
                        <h1 class="ml-4 mt-10 display-2">Информационный ресурс</h1>
                        <v-card
                                class="mt-10"
                                v-model="checkStatus"
                                outlined
                                raised
                                color="transparent"
                        >
                            <v-tabs
                                    v-model="tab"
                                    fixed-tabs
                                    bacground-color="indigo"
                                    dark
                            >
                                <v-tab
                                        :key="'login'"
                                >
                                    Авторизация
                                </v-tab>
                                <v-tab
                                        active-class
                                        :key="'registration'"
                                >
                                    Регистрация
                                </v-tab>
                            </v-tabs>

                            <v-tabs-items
                                    v-model="tab"
                            >

                                <v-tab-item
                                        :key="'login'"
                                >
                                    <form class="pa-12 text-center">

                                        <v-text-field
                                                v-model="joinUsername"
                                                prepend-icon="person"
                                                label="Логин"
                                        ></v-text-field>
                                        <v-text-field
                                                v-model="joinPassword"
                                                prepend-icon="lock"
                                                type="password"
                                                label="Пароль"
                                        ></v-text-field>

                                        <v-btn class="mt-3 primary"
                                               :elevation="6"
                                               rounded
                                               @click="login"
                                        >
                                            <v-icon left>account_circle</v-icon>
                                            Войти
                                        </v-btn>
                                        <v-overlay :value="overlayLogin">
                                            <v-progress-circular indeterminate size="64"></v-progress-circular>
                                        </v-overlay>
                                    </form>
                                </v-tab-item>

                                <v-tab-item
                                        :key="'registration'"
                                >
                                    <form class="pa-12 text-center">
                                        <v-text-field
                                                v-model="firstname"
                                                prepend-icon="edit"
                                                :error-messages="firstnameErrors"
                                                color="teal"
                                                label="Имя"
                                                required
                                                @input="$v.firstname.$touch()"
                                                @blur="$v.firstname.$touch()"
                                        ></v-text-field>

                                        <v-text-field
                                                v-model="lastname"
                                                prepend-icon="edit"
                                                :error-messages="lastnameErrors"
                                                color="teal"
                                                label="Фамилия"
                                                persistent-hint
                                                required
                                                @input="$v.lastname.$touch()"
                                                @blur="$v.lastname.$touch()"
                                        ></v-text-field>

                                        <v-text-field
                                                v-model="middlename"
                                                prepend-icon="edit"
                                                :error-messages="middlenameErrors"
                                                color="teal"
                                                label="Отчество"
                                                persistent-hint
                                                required
                                                @input="$v.middlename.$touch()"
                                                @blur="$v.middlename.$touch()"
                                        ></v-text-field>

                                        <v-text-field
                                                v-model="age"
                                                prepend-icon="edit"
                                                :error-messages="ageErrors"
                                                color="teal"
                                                label="Возраст"
                                                persistent-hint
                                                required
                                                @input="$v.age.$touch()"
                                                @blur="$v.age.$touch()"
                                        ></v-text-field>

                                        <v-text-field
                                                v-model="email"
                                                prepend-icon="email"
                                                :error-messages="emailErrors"
                                                color="teal"
                                                label="Почта"
                                                required
                                                @input="$v.email.$touch()"
                                                @blur="$v.email.$touch()"
                                        ></v-text-field>

                                        <v-text-field
                                                v-model="username"
                                                prepend-icon="person"
                                                :error-messages="usernameErrors"
                                                color="teal"
                                                label="Логин"
                                                required
                                                @input="$v.username.$touch()"
                                                @blur="$v.username.$touch()"
                                        ></v-text-field>

                                        <v-text-field
                                                v-model="password"
                                                prepend-icon="lock"
                                                :error-messages="passwordErrors"
                                                color="teal"
                                                label="Пароль"
                                                type="password"
                                                required
                                                @input="$v.password.$touch()"
                                                @blur="$v.password.$touch()"
                                        ></v-text-field>

                                        <v-combobox
                                                v-model="userRole"
                                                :items="roles"
                                                :error-messages="rolesErrors"
                                                label="Роль пользователя"
                                                color="teal"
                                                prepend-icon="how_to_reg"
                                                append-icon="arrow_drop_down"
                                                clear-icon="close"
                                                required
                                                clearable
                                                small-chips
                                                @input="$v.userRole.$touch()"
                                                @blur="$v.userRole.$touch()"
                                        ></v-combobox>
                                        <v-btn color="mt-5 teal white--text darken-1" :elevation="6" rounded @click="registration()"
                                        >
                                            Регистрация
                                            <v-icon right>check</v-icon>
                                        </v-btn>
                                        <v-overlay :value="overlayRegistration">
                                            <v-progress-circular indeterminate size="64"></v-progress-circular>
                                        </v-overlay>
                                    </form>
                                </v-tab-item>
                            </v-tabs-items>
                        </v-card>
                    </v-col>
                </v-row>
                <v-dialog v-model="isAuthError" persistent max-width="450px">
                    <v-card>
                        <v-img
                                class="white--text primary darken-3"
                                height="55px"
                        >
                            <v-card-title class="justify-center">
                                <span class="headline">Ошибка</span>
                            </v-card-title>
                        </v-img>
                        <v-card-text>
                            <v-container>
                                <div>
                                    <p class="text-center">
                                        Неправильный логин или пароль!
                                    </p>
                                </div>
                            </v-container>
                        </v-card-text>
                        <v-card-actions class="justify-center">
                            <v-btn color="primary darken-1" rounded @click="isAuthError = false">Закрыть</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
                <v-dialog v-model="isMessageActivate" persistent max-width="450px">
                    <v-card>
                        <v-img
                                class="white--text primary darken-3"
                                height="55px"
                        >
                            <v-card-title class="justify-center">
                                <span class="headline">Активация</span>
                            </v-card-title>
                        </v-img>
                        <v-card-text>
                            <v-container>
                                <div>
                                    <p class="text-center font-weight-black">Регистрация прошла успешно!</p>
                                    <p class="text-center">
                                        Здравствуйте. Для активации учетной записи было отправлено сообщение на ваш почтовый ящик с последующим подтверждением.
                                    </p>
                                </div>
                            </v-container>
                        </v-card-text>
                        <v-card-actions class="justify-center">
                            <v-btn color="primary darken-1" rounded @click="isMessageActivate = false">Закрыть</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
                <v-dialog v-model="codes" persistent max-width="450px">
                    <v-card>
                        <v-img
                                class="white--text primary darken-3"
                                height="55px"
                        >
                            <v-card-title class="justify-center">
                                <span class="headline">Активация</span>
                            </v-card-title>
                        </v-img>
                        <v-card-text>
                            <v-container>
                                <div>
                                    <p class="text-center">
                                        Активация учетной записи прошла успешно!
                                    </p>
                                </div>
                            </v-container>
                        </v-card-text>
                        <v-card-actions class="justify-center">
                            <v-btn color="primary darken-1" rounded @click="closeActivateMessage()">Закрыть</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
                <v-dialog v-model="isRegistrationError" persistent max-width="450px">
                    <v-card>
                        <v-img
                                class="white--text primary darken-3"
                                height="55px"
                        >
                            <v-card-title class="justify-center">
                                <span class="headline">Ошибка</span>
                            </v-card-title>
                        </v-img>
                        <v-card-text>
                            <v-container>
                                <div>
                                    <p class="mt-5 text-center">
                                        Данный логин уже занят. Введите другой логин!
                                    </p>
                                </div>
                            </v-container>
                        </v-card-text>
                        <v-card-actions class="justify-center">
                            <v-btn color="primary darken-1" rounded @click="isRegistrationError = false">Закрыть</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import { mapState, mapActions, mapGetters } from 'vuex'
    import { validationMixin } from 'vuelidate'
    import { required, maxLength, minLength, email } from 'vuelidate/lib/validators'

    export default {
        name: 'AuthorizationForm',
        mixins: [validationMixin],
        validations: {
            firstname: { required },
            lastname: { required },
            middlename: { required },
            age: { required },
            email: { required, email },
            username: { required, minLength: minLength(5) },
            password: { required, minLength: minLength(8) },
            userRole: { required }
        },
        data() {
            return {
                joinUsername: '',
                joinPassword: '',
                username: '',
                password: '',
                checkStatus: true,
                tab: null,
                id: '',
                firstname: '',
                lastname: '',
                middlename: '',
                age: '',
                email: '',
                roles: [
                    'Психолог',
                    'Пользователь'
                ],
                userRole: '',
                isMessageActivate: false,
                isAuthError: false,
                isRegistrationError: false,
                overlayLogin: false,
                overlayRegistration: false
            }
        },
        methods: {
            ...mapActions(['loginAction']),
            ...mapGetters(['getAuthStatus', 'getCode']),
            ...mapActions(['addUserAction', 'setCodes']),
            ...mapGetters(['findUsers']),
            login() {
                const user = {
                    username: this.joinUsername,
                    password: this.joinPassword
                }

                this.loginAction(user)

                this.joinUsername = ''
                this.joinPassword = ''

                this.overlayLogin = !this.overlayLogin
            },
            registration() {
                this.$v.$touch()
                if (!this.$v.$invalid) {
                    const role = this.userRole === 'Психолог' ? [ 'ADMIN' ] : [ 'USER' ]
                    const user = {
                        id: this.id,
                        firstname: this.firstname,
                        lastname: this.lastname,
                        middlename: this.middlename,
                        age: this.age,
                        email: this.email,
                        username: this.username,
                        password: this.password,
                        roles: role
                    }

                    this.addUserAction(user)

                    this.id = ''
                    this.firstname = ''
                    this.lastname = ''
                    this.middlename = ''
                    this.age = ''
                    this.email = ''
                    this.username = ''
                    this.password = ''
                    this.userRole = ''
                    this.$v.$reset()

                    this.overlayRegistration = !this.overlayRegistration
                }
            },
            closeActivateMessage() {
                this.setCodes(false)
            }
        },
        computed: {
            ...mapState(['codes', 'registrationStatus']),
            firstnameErrors () {
                const errors = []
                if (!this.$v.firstname.$dirty) return errors
                !this.$v.firstname.required && errors.push('Поле Имя должно быть заполнено!')
                return errors
            },
            lastnameErrors () {
                const errors = []
                if (!this.$v.lastname.$dirty) return errors
                !this.$v.lastname.required && errors.push('Поле Фамилия должно быть заполнена!')
                return errors
            },
            middlenameErrors () {
                const errors = []
                if (!this.$v.middlename.$dirty) return errors
                !this.$v.middlename.required && errors.push('Поле Отчество должно быть заполнено!')
                return errors
            },
            ageErrors () {
                const errors = []
                if (!this.$v.age.$dirty) return errors
                !this.$v.age.required && errors.push('Поле Возраст должно быть заполнено!')
                return errors
            },
            emailErrors () {
                const errors = []
                if (!this.$v.email.$dirty) return errors
                !this.$v.email.email && errors.push('Почта некорректна!')
                !this.$v.email.required && errors.push('Поле Почта должно быть заполнено!')
                return errors
            },
            usernameErrors () {
                const errors = []
                if (!this.$v.username.$dirty) return errors
                !this.$v.username.minLength && errors.push('Логин должен содержать не менее 5 символов!')
                !this.$v.username.required && errors.push('Логин должен быть заполнен!')
                return errors
            },
            passwordErrors () {
                const errors = []
                if (!this.$v.password.$dirty) return errors
                !this.$v.password.minLength && errors.push('Пароль должен содержать не менее 8 символов!')
                !this.$v.password.required && errors.push('Пароль должен быть заполнен!')
                return errors
            },
            rolesErrors () {
                const errors = []
                if (!this.$v.userRole.$dirty) return errors
                !this.$v.userRole.required && errors.push('Роль пользователя должна быть указана!')
                if (this.userRole !== 'Психолог' && this.userRole !== 'Пользователь') {
                    errors.push('Неправильна указана роль!')
                }
                return errors
            },
        },
        watch: {
            overlayLogin (val) {
                val && setTimeout(() => {
                    if (this.getAuthStatus() === 'success') {
                        this.$router.push('/')
                    } else {
                        this.isAuthError = true
                    }
                    this.overlayLogin = false
                }, 1500)
            },
            overlayRegistration (val) {
                val && setTimeout(() => {
                    if (this.registrationStatus === 'success') {
                        this.isMessageActivate = true
                    } else {
                        this.isRegistrationError = true
                    }
                    this.overlayRegistration = false
                }, 3000)
            }
        },
    }

</script>

<style>
    .login-background {
        background-image: url('/img/log_reg_background.jpg');
        background-position: center;
        background-repeat:  no-repeat;
        background-attachment: fixed;
        background-size:  cover;
        background-color: #999;
    }
    .size-image {
        width: 150px;
        flex-grow: 2;
        border-radius: 2px;
        transition: width 1s;
    }
</style>