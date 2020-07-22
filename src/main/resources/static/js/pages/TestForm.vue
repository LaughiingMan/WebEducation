<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-row align="center" class="mr-5 ml-5">
        <v-col>
            <div class="text-left">
                <p class="mt-4 text-center primary--text font-weight-bold font-italic">ТЕСТИРОВАНИЕ</p>
            </div>
            <div v-if="this.isTable">
                <v-layout>
                    <div class="flex-grow-1"></div>
                    <v-col cols="12" sm="6" md="4">
                        <v-text-field v-model="search" class="mt-4 mr-5" prepend-icon="search" clear-icon="close" clearable label="Поиск"></v-text-field>
                    </v-col>
                </v-layout>
                <v-data-table
                        :headers="headers"
                        :items="items"
                        :search="search"
                        sort-by="calories"
                        :page.sync="page"
                        :items-per-page="itemsPerPage"
                        hide-default-footer
                        class="elevation-1"
                        @page-count="pageCount = $event"
                >
                    <template v-slot:item.createDate="{ item }">
                        <span>{{new Date(item.createDate).toLocaleDateString()}}</span>
                    </template>
                    <template v-slot:item.author="{ item }">
                        <span>{{item.author.firstname + ' ' + item.author.lastname + ' ' + item.author.middlename}}</span>
                    </template>
                    <template v-slot:item.action="{ item }">
                        <v-btn
                                class="primary--text p-3"
                                text
                                rounded
                                @click="showItem(item)"
                        >
                            Показать
                        </v-btn>
                    </template>
                </v-data-table>
                <div class="text-center pt-2">
                    <v-pagination
                            v-model="page"
                            :length="pageCount"
                            circle
                            next-icon="chevron_right"
                            prev-icon="chevron_left"
                    ></v-pagination>
                </div>
                <v-overlay :value="overlay">
                    <v-progress-circular indeterminate size="64"></v-progress-circular>
                </v-overlay>
            </div>
            <div v-if="this.isItem">
                <v-container>
                    <v-row justify="center" dense>
                        <v-col class="mt-10" cols="8">
                            <v-card class="d-flex flex-column" :height="this.getWindowHeight() / 1.3">
                                <v-card-title class="ml-4 mt-10 headline">{{ currentItem.name }}</v-card-title>

                                <v-card-text class="ml-10 text--primary">
                                    <v-col class="mt-10" cols="11">
                                        <p class="text-justify">
                                            {{ currentItem.description }}
                                        </p>
                                    </v-col>
                                </v-card-text>

                                <v-spacer></v-spacer>

                                <v-card-actions class="mb-10">
                                    <v-btn class="ml-10 red--text" @click="isShowItem()" text>Закрыть</v-btn>
                                    <div class="flex-grow-1"></div>
                                    <v-btn class="mr-10 green--text" @click="startTest()" text>Начать</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-col>
                    </v-row>
                </v-container>
            </div>
        </v-col>
    </v-row>
</template>

<script>
    import { mapGetters, mapActions, mapState } from 'vuex'

    export default {
        data: () => ({
            ...mapGetters(['getAllTests', 'getWindowHeight', 'getState']),
            ...mapActions(['findTestsAction', 'setIsTable', 'setIsItem', 'setCurrentItem', 'findImagesByTestAction', 'findAudiosByTestAction']),
            search: '',
            length: 3,
            window: 0,
            menu2: false,
            date: new Date().toISOString().substr(0, 10),
            dialog: false,
            page: 1,
            pageCount: 2,
            headers: [
                { text: 'Наименование', value: 'name', class: "primary white--text font-weight-bold" },
                { text: 'Автор', value: 'author', class: "primary white--text font-weight-bold" },
                { text: 'Дата создания', value: 'createDate', class: "primary white--text font-weight-bold" },
                { text: 'Действия', value: 'action', sortable: false, align: 'center', class: "primary white--text font-weight-bold" },
            ],
            desserts: [],
            editedIndex: -1,
            overlay: false
        }),

        computed: {
            ...mapState(['isTable', 'isItem', 'currentItem']),
            formTitle () {
                return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
            },
            items: {
                get() {
                    return this.getAllTests()
                }
            }
        },

        watch: {
            dialog (val) {
                val || this.close()
            },
            overlay (val) {
                val && setTimeout(() => {
                    this.$router.push('testing/' + this.currentItem.id)
                    this.overlay = false
                }, 1000)
            },
        },

        methods: {
            isShowItem () {
                this.setIsTable(true)
                this.setIsItem(false)
            },

            startTest () {
                this.setIsTable(true)
                this.setIsItem(false)
                this.findImagesByTestAction(this.currentItem)
                this.findAudiosByTestAction(this.currentItem)
                this.overlay = !this.overlay
            },

            showItem (item) {
                this.setIsTable(false)
                this.setIsItem(true)
                this.setCurrentItem(Object.assign({}, item))
            },

            deleteItem (item) {
                const index = this.desserts.indexOf(item)
                confirm('Are you sure you want to delete this item?') && this.desserts.splice(index, 1)
            },
        },
    }
</script>

<style>
</style>