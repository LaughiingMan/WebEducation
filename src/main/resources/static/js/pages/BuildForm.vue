<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-row align="center" class="mr-5 ml-5">
        <v-col>
            <div class="text-left">
                <p class="mt-4 text-center primary--text font-weight-bold font-italic">КОНСТРУКТОР</p>
            </div>
            <v-card class="d-flex flex-column">

                <v-card-text class="text--primary">
                    <v-layout
                            class="mt-10 custom-border"
                    >
                        <div class="text-left">
                            <p class="mt-4 ml-4 text-center primary--text font-weight-bold font-italic">Вопрос</p>
                        </div>
                        <v-row class="mt-10" justify="center">
                            <v-col sm="8">
                                <div class="overflow-hidden">
                                    <v-bottom-navigation
                                            color="indigo"
                                            :value="0"
                                    >
                                        <v-btn @click="isTextOption">
                                            <span>Текстовый вариант</span>
                                            <v-icon>book</v-icon>
                                        </v-btn>

                                        <v-btn @click="isImageOption">
                                            <span>Картинный вариант</span>
                                            <v-icon>image</v-icon>
                                        </v-btn>

                                        <v-btn @click="isAudioOption">
                                            <span>Аудио вариант</span>
                                            <v-icon>audiotrack</v-icon>
                                        </v-btn>
                                    </v-bottom-navigation>
                                </div>
                                <v-row justify="center">
                                    <v-col sm="8">
                                        <v-textarea
                                                class="mt-10"
                                                label="Текст"
                                                rows="2"
                                                v-model="textQuestion"
                                        ></v-textarea>
                                    </v-col>
                                </v-row>
                                <component
                                        v-bind:is="component"
                                        v-model="valueQuestion"
                                ></component>
                            </v-col>
                        </v-row>
                    </v-layout>
                    <v-layout
                            class="mt-10 custom-border"
                    >
                        <div class="text-left">
                            <p class="mt-4 ml-4 text-center primary--text font-weight-bold font-italic">Ответ</p>
                        </div>
                        <v-row class="mt-10" justify="center">
                            <v-col sm="6">
                                <div class="mt-5 text-center">
                                    <div v-if="isNewAnswer" v-for="item in valueAnswers">
                                        <v-row>
                                            <v-col sm="9">
                                                <v-text-field
                                                        :label="item.caption"
                                                        v-model="item.value"
                                                ></v-text-field>
                                            </v-col>
                                            <v-col sm="3">
                                                <v-text-field
                                                        :label="item.weight"
                                                        v-model="item.number"
                                                ></v-text-field>
                                            </v-col>
                                        </v-row>
                                    </div>
                                    <v-btn class="mt-5 mr-5" color="red" @click="removeAnswer" dark icon>
                                        <v-icon x-large>close</v-icon>
                                    </v-btn>
                                    <v-btn class="mt-5 ml-5" color="green" @click="addAnswer" dark icon>
                                        <v-icon x-large>add</v-icon>
                                    </v-btn>
                                </div>
                            </v-col>
                        </v-row>
                    </v-layout>
                </v-card-text>

                <v-spacer></v-spacer>

                <v-card-actions class="mt-5 mb-10 mr-10 justify-end">
                    <v-btn class="mr-10" color="green" @click="createTest" dark text rounded>Создать и сохранить</v-btn>
                    <v-btn color="primary" @click="nextQuest" dark text rounded>Следующий вопрос</v-btn>
                </v-card-actions>
            </v-card>
            <v-dialog v-model="dialog" persistent max-width="800px">
                <v-card>
                    <v-card-title>
                        <span class="ml-5 headline primary--text">Создание</span>
                    </v-card-title>
                    <v-card-text>
                        <v-container>
                            <v-row justify="center">
                                <v-col cols="12" sm="11">
                                    <v-text-field label="Наименование теста" v-model="nameTest" required></v-text-field>
                                </v-col>
                                <v-col cols="12" sm="11">
                                    <v-textarea label="Описание теста" v-model="descriptionTest" required></v-textarea>
                                </v-col>
                                <div class="col-sm-11 col-12" v-if="isNewResult" v-for="item in result">
                                    <v-textarea
                                            :label="item.caption"
                                            v-model="item.value"
                                    ></v-textarea>
                                </div>
                                <v-col cols="12">
                                    <v-btn class="mt-5 ml-7" color="red" @click="removeResult" dark icon>
                                        <v-icon x-large>close</v-icon>
                                    </v-btn>
                                    <v-btn class="mt-5 ml-5" color="green" @click="addResult" dark icon>
                                        <v-icon x-large>add</v-icon>
                                    </v-btn>
                                </v-col>
                            </v-row>
                        </v-container>
                        <small class="ml-10 red--text">*указывает на обязательное поле</small>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="red darken-1" text @click="dialog = false">Закрыть</v-btn>
                        <v-btn class="mr-7" color="green darken-1" text @click="saveTest">Сохранить</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-col>
    </v-row>
</template>

<script>
    import BuildingImageForm from '../components/BuildingImageForm.vue'
    import BuildingTextForm from '../components/BuildingTextForm.vue'
    import BuildingAudioForm from '../components/BuildingAudioForm.vue'
    import { mapActions, mapState } from 'vuex'
    import { uuid } from 'vue-uuid'

    export default {
        data: () => ({
            name: 'BuildForm',
            selected: '',
            dialog: false,
            component: BuildingTextForm,
            showSettings: true,
            isNewAnswer: false,
            idTest: '',
            valueQuestion: '',
            valueAnswers: [],
            resultQuestions: [],
            resultAnswers: [],
            countQuestions: 0,
            isNewResult: false,
            result: [],
            nameTest: '',
            descriptionTest: '',
            images: [],
            audios: [],
            textQuestion: ''
        }),
        destroyed() {
            this.findTestsAction()
        },
        methods: {
            ...mapActions(['saveTestAction', 'findTestsAction', 'saveImagesAction', 'saveAudiosAction']),
            isTextOption() {
                this.component = BuildingTextForm
            },
            isImageOption() {
                this.component = BuildingImageForm
            },
            isAudioOption() {
                this.component = BuildingAudioForm
            },
            addAnswer() {
                this.isNewAnswer = true;
                const countAnswer = this.valueAnswers.length + 1;
                this.valueAnswers.push({id: countAnswer, caption: 'Ответ ' + countAnswer, value : '', weight: 'Номер результата',
                    number: ''})
            },
            removeAnswer() {
                this.valueAnswers.pop()
            },
            nextQuest() {
                this.resultQuestions.push({id: this.countQuestions, questComponent: this.component.name,
                    value: this.selectQuestComponent()});
                this.resultAnswers.push({id: this.countQuestions, value: [...this.valueAnswers]});
                this.valueAnswers = [];
                this.textQuestion = ''
                this.countQuestions++;
            },
            createTest() {
                this.dialog = true;
            },
            saveTest() {
                const test = {
                    id: this.idTest,
                    name: this.nameTest,
                    description: this.descriptionTest,
                    author: this.currentUser,
                    createDate: new Date().toISOString(),
                    result: JSON.stringify(this.result),
                    resultAnswers: JSON.stringify(this.resultAnswers),
                    resultQuestions: JSON.stringify(this.resultQuestions)
                };
                this.selectSaveTest(test)
                this.dialog = false
            },
            addResult() {
                this.isNewResult = true;
                const countResult = this.result.length + 1;
                this.result.push({id: countResult, caption: 'Результат ' + countResult, value : '', count: countResult})
            },
            removeResult() {
                this.result.pop()
            },
            selectQuestComponent() {
                switch (this.component.name) {
                    case 'BuildingTextForm':
                        return this.textQuestion.slice()
                    case 'BuildingImageForm':
                        const imageUuid = uuid.v1()
                        const testImage = {
                            id: '',
                            guid: imageUuid,
                            test: null,
                            image: this.valueQuestion
                        }
                        this.images.push(testImage)
                        return {
                            imageUuid: imageUuid,
                            text: this.textQuestion
                        }
                    case 'BuildingAudioForm':
                        const audioUuid = uuid.v1()
                        const testAudio = {
                            id: '',
                            guid: audioUuid,
                            test: null,
                            audio: this.valueQuestion
                        }
                        this.audios.push(testAudio)
                        return {
                            audioUuid: audioUuid,
                            text: this.textQuestion
                        }
                }
            },
            selectSaveTest(test) {
                const resultTest = {
                    test: test,
                    image: this.images,
                    audio: this.audios,
                }
                this.saveTestAction(resultTest)
            }
        },
        computed: {
            ...mapState(['currentUser'])
        }
    }
</script>

<style>
    .custom-border {
        border: 1px solid dodgerblue;
    }
</style>