<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-row align="center" class="mr-5 ml-5">
        <v-col>
            <div class="text-left">
                <p class="mt-4 text-center primary--text font-weight-bold font-italic">ПОЛЬЗОВАТЕЛИ</p>
            </div>
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
                    hide-default-footer
                    class="elevation-1"
                    @page-count="pageCount = $event"
            >
                <template v-slot:no-data>
                    <v-btn color="primary" @click="initialize">Обновить</v-btn>
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
        </v-col>
    </v-row>
</template>

<script>
    import { mapGetters } from 'vuex'

    export default {
        name: 'UsersForm',
        data() {
            return {
                ...mapGetters(['getAllUsers']),
                search: '',
                length: 3,
                page: 2,
                pageCount: 2,
                headers: [
                    { text: 'Имя', value: 'firstname', class: "primary white--text font-weight-bold" },
                    { text: 'Фамилия', value: 'lastname', class: "primary white--text font-weight-bold" },
                    { text: 'Отчество', value: 'middlename', class: "primary white--text font-weight-bold" },
                    { text: 'Возраст', value: 'age', class: "primary white--text font-weight-bold" }
                ]
            }
        },

        computed: {
            items: {
                get() {
                    return this.getAllUsers()
                }
            }
        },
    }
</script>

<style>

</style>