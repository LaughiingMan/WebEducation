<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-row align="center" class="mr-5 ml-5">
        <v-col>
            <div class="text-left">
                <p class="mt-4 text-center primary--text font-weight-bold font-italic">СТАТИСТИКА</p>
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
                <template v-slot:item.createDate="{ item }">
                    <span>{{new Date(item.createDate).toLocaleDateString()}}</span>
                </template>
                <template v-slot:item.user="{ item }">
                    <span>{{item.user.firstname + ' ' + item.user.lastname + ' ' + item.user.middlename}}</span>
                </template>
                <template v-slot:item.action="{ item }">
                    <v-icon
                            small
                            class="mr-2"
                            @click="editItem(item)"
                    >
                        person
                      </v-icon>
                </template>
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
            <v-dialog v-model="dialog" max-width="1000px">
                <v-card>
                    <v-card-text>
                        <v-container>
                            <v-row
                                    justify="center"
                            >
                                <v-col>
                                    <v-subheader
                                            class="mt-4 title text-center primary--text font-weight-bold font-italic"
                                            v-text="editedItem.userName"
                                    ></v-subheader>
                                    <v-subheader
                                            class="mt-4 font-weight-bold font-italic"
                                            v-text="editedItem.name"
                                    ></v-subheader>
                                    <p class="ml-10 mr-10 mt-10 text-justify"> {{ editedItem.result }}</p>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-card-text>

                    <v-card-actions class="mr-10">
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="close">Закрыть</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            <div class="mt-10 text-center">
                <v-btn color="red" @click="showStatistics = !showStatistics" outlined large fab dark>
                    <v-icon dark>
                        bar_chart
                    </v-icon>
                </v-btn>
            </div>
            <v-card v-if="showStatistics" class="mt-10 d-flex flex-column">
                <v-card-title class="mt-10 justify-center headline">
                    <v-row class="justify-center">
                        <v-col sm="6">
                            <v-combobox
                                    v-model="currentTest"
                                    :items="currentItems"
                                    label="Тест"
                                    color="primary"
                                    prepend-icon="how_to_reg"
                                    append-icon="arrow_drop_down"
                                    clear-icon="close"
                                    clearable
                                    small-chips
                                    @change="fillStatistics"
                            ></v-combobox>
                        </v-col>
                    </v-row>
                </v-card-title>

                <v-card-text class="text--primary">
                    <div class="chart">
                        <bar-chart
                                :data="chartValue"
                                label-rotate
                                rainbow
                                legend
                                labelRotateDeg="0"
                        />
                    </div>
                    <v-row class="justify-center">
                        <v-col sm="11">
                            <v-expansion-panels class="mt-5" inset>
                                <v-expansion-panel
                                        v-for="(item,i) in temps"
                                        :key="i"
                                >
                                    <v-expansion-panel-header>{{item.name}}</v-expansion-panel-header>
                                    <v-expansion-panel-content>
                                        {{item.result}}
                                                         </v-expansion-panel-content>
                                </v-expansion-panel>
                            </v-expansion-panels>
                        </v-col>
                    </v-row>
                </v-card-text>

                <v-spacer></v-spacer>

                <!--<v-card-actions class="mb-10 justify-center">
                    <v-btn color="red" @click="backTest()" dark text rounded>Вернутся к тестам</v-btn>
                </v-card-actions>-->
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
    import { mapGetters } from 'vuex'

    export default {
        name: 'StatisticsForm',
        data: () => ({
            ...mapGetters(['getAllStatistics']),
            search: '',
            dialog: false,
            showStatistics: false,
            length: 3,
            chartValue: [
                {
                    name: "Количество",
                    data: []
                }
            ],
            page: 2,
            pageCount: 2,
            headers: [
                { text: 'Тест', value: 'test.name', class: "primary white--text font-weight-bold" },
                { text: 'Пользователь', value: 'user', class: "primary white--text font-weight-bold" },
                { text: 'Дата создания', value: 'createDate', class: "primary white--text font-weight-bold" },
                { text: 'Действия', value: 'action', sortable: false, class: "primary white--text font-weight-bold" },
            ],
            temps: [],
            editedItem: {},
            currentTest: ''
        }),
        computed: {
            items: {
                get() {
                    return this.getAllStatistics()
                }
            },
            currentItems: {
                get() {
                    return this.items.flatMap(e => [e.test.name])
                }
            }
        },
        methods: {
            fillStatistics() {
                this.chartValue[0].data = []
                this.temps = []
                const chartDataValue = this.chartValue[0].data
                const filterTest = this.items.filter(item => item.test.name === this.currentTest)
                if (filterTest.length > 0) {
                    const findResults = JSON.parse(filterTest[0].test.result);
                    findResults.forEach(e => {
                        chartDataValue.push({ label: e.caption, value: 0 })
                        this.temps.push({name: e.caption, result: e.value})
                    })
                }
                filterTest.forEach(item => {
                    const findItem = chartDataValue.find(e => e.label === item.name)
                    findItem.value = findItem.value + 1
                })
            },
            editItem (item) {
                this.editedItem = {
                    name: item.name,
                    userName: item.user.lastname + ' ' + item.user.firstname + ' ' + item.user.middlename,
                    result: item.result
                }
                this.dialog = true
            },

            close () {
                this.dialog = false
                setTimeout(() => {
                    this.editedItem = {}
                }, 300)
            },
        }
    }
</script>

<style>
    .chart {
        width: 100%;
        height: 300px;
    }
</style>