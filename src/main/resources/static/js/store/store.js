import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import registrationApi from 'api/registration'
import loginApi from 'api/login'
import usersApi from 'api/users'
import testApi from 'api/tests'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)
Vue.use(axios)

export default new Vuex.Store({
    plugins: [
        createPersistedState({
            storage: window.sessionStorage
        })
    ],
    state: {
        codes: false,
        status: '',
        statusSettings: '',
        users: [],
        tests: [],
        images: [],
        audios: [],
        test: {},
        statistics: [],
        registrationStatus: '',
        currentUser: {},
        isTable: true,
        isItem: false,
        isSettings: false,
        currentItem: {
            id: '',
            name: '',
            description: '',
            author: null,
            createDate: '',
            result: [],
            resultAnswers: [],
            resultQuestions: []
        },
        localhost: 'http://localhost:8080',
        joinNow: false,
        windowHeight: window.innerHeight
    },
    getters: {
        getState: state => state,
        getAuthStatus: state => state.status,
        getAllUsers: state => state.users,
        getAllTests: state => state.tests,
        getAllStatistics: state => state.statistics,
        getWindowHeight: state => state.windowHeight,
        isJoinNow: state => state.joinNow
    },
    mutations: {
        checkActivatedCodeMutation(state, code) {
            state.codes = code
        },
        loginSuccessMutation(state, user) {
            state.status = 'success'
            state.currentUser = user
        },
        loginErrorMutation(state) {
            state.status = 'error'
        },
        addUserMutation(state, status) {
            state.registrationStatus = status
        },
        saveSuccessSettingsMutation(state, user) {
            state.statusSettings = 'success'
            state.currentUser = user
        },
        saveErrorSettingsMutation(state) {
            state.statusSettings = 'error'
        },
        findUsersMutation(state, users) {
            state.users = users
        },
        findTestsMutation(state, tests) {
            state.tests = tests
        },
        findTestByIdMutation(state, test) {
            state.test = test
        },
        findImagesByTestMutation(state, images) {
            state.images = images
        },
        findAudiosByTestMutation(state, audios) {
            state.audios = audios
        },
        findAuthUserMutation(state, user) {
            state.currentUser = user
        },
        findAllStatisticsMutation(state, statistics) {
            state.statistics = statistics
        },
        SET_IS_TABLE: (state, newValue) => {
            state.isTable = newValue
        },
        SET_IS_ITEM: (state, newValue) => {
            state.isItem = newValue
        },
        SET_CURRENT_ITEM: (state, newValue) => {
            state.currentItem = newValue
        },
        SET_TEST: (state, newValue) => {
            state.test = newValue
        },
        SET_CODES: (state, newValue) => {
            state.codes = newValue
        },
        SET_SETTINGS: (state, newValue) => {
            state.isSettings = newValue
        }
    },
    actions: {
        async addUserAction({commit, state}, user) {
            const response = await axios.post(state.localhost + '/registration', user)
            if (response.data === null || response.data === '') {
                commit('addUserMutation', 'error')
            } else {
                commit('addUserMutation', 'success')
            }
        },
        async checkActivatedCodeAction({commit, state}, code) {
            const response = await axios.post(state.localhost + '/activated', code)
            commit('checkActivatedCodeMutation', response.data)
        },
        async loginAction({commit, state}, user) {
            try {
                const response = await axios.post(state.localhost + '/login', user)
                window.$cookies.set('jwt.token', 'Bearer_' + response.data.token, '3600s')
                window.$cookies.set('roles', response.data.user.roles, '3600s')
                commit('loginSuccessMutation', response.data.user)
            } catch(e) {
                commit('loginErrorMutation')
            }
        },
        async findUsersAction({commit, state}) {
            const response = await axios.get(state.localhost + '/profile/all_users')
            commit('findUsersMutation', response.data)
        },
        async findTestsAction({commit, state}) {
            const response = await axios.get(state.localhost + '/profile/all_tests')
            commit('findTestsMutation', response.data)
        },
        async saveTestAction({commit, state}, test) {
            await axios.post(state.localhost + '/profile/save_tests', test)
        },
        async findTestByIdAction({commit, state}, id) {
            const response = await axios.post(state.localhost + '/profile/id_test', id, {
                headers: {
                    'Content-Type': 'application/json',
                }
            })
            commit('findTestByIdMutation', response.data)
        },
        async saveSettingsAction({commit, state}, user) {
            try {
                const response = await axios.post(state.localhost + '/menu/save_settings', user)
                commit('saveSuccessSettingsMutation', response.data)
            } catch (e) {
                commit('saveErrorSettingsMutation')
            }
        },
        async saveStatisticsAction({commit, state}, statistics) {
            await axios.post(state.localhost + '/profile/save_statistics', statistics)
        },
        async findImagesByTestAction({commit, state}, test) {
            const response = await axios.post(state.localhost + '/profile/id_image', test)
            commit('findImagesByTestMutation', response.data)
        },
        async findAudiosByTestAction({commit, state}, test) {
            const response = await axios.post(state.localhost + '/profile/id_audio', test)
            commit('findAudiosByTestMutation', response.data)
        },
        async findAuthUserAction({commit, state}) {
            const response = await axios.get(state.localhost + '/menu/find_user')
            commit('findAuthUserMutation', response.data)
        },
        async findAllStatisticsAction({commit, state}) {
            const response = await axios.get(state.localhost + '/profile/all_statistics')
            commit('findAllStatisticsMutation', response.data)
        },
        setIsTable({commit, state}, newValue) {
            commit('SET_IS_TABLE', newValue)
            return state.isTable
        },
        setIsItem({commit, state}, newValue) {
            commit('SET_IS_ITEM', newValue)
            return state.isItem
        },
        setCurrentItem({commit, state}, newValue) {
            commit('SET_CURRENT_ITEM', newValue)
            return state.currentItem
        },
        setTest({commit, state}, newValue) {
            commit('SET_TEST', newValue)
            return state.test
        },
        setCodes({commit, state}, newValue) {
            commit('SET_CODES', newValue)
            return state.codes
        },
        setSettings({commit, state}, newValue) {
            commit('SET_SETTINGS', newValue)
            return state.isSettings
        }
    }
})