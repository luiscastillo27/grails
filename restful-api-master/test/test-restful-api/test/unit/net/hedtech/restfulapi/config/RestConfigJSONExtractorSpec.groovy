/* ****************************************************************************
 * Copyright 2013 Ellucian Company L.P. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************/

package net.hedtech.restfulapi.config

import grails.test.mixin.*
import grails.test.mixin.support.*

import net.hedtech.restfulapi.*
import net.hedtech.restfulapi.extractors.configuration.*
import net.hedtech.restfulapi.extractors.json.*
import net.hedtech.restfulapi.marshallers.json.*

import spock.lang.*


@TestMixin(GrailsUnitTestMixin)
class RestConfigJSONExtractorSpec extends Specification {

    def "Test json extractor template parsing"() {
        setup:
        def src =
        {
            jsonExtractorTemplates {
                template 'one' config {
                }

                template 'two' config {
                    inherits = ['one']
                    property 'person.name' name 'bar'
                    property 'person.address' flatObject true
                    property 'person.customer' shortObject true
                    property 'lastName' defaultValue 'Smith'
                    property 'date' date true
                    lenientDates = true
                    dateFormats = ['yyyy.MM.dd', 'yyyy/MM/dd']
                    shortObject { def v -> 'short' }
                }
            }
        }

        when:
        def config = RestConfig.parse( grailsApplication, src )
        config.validate()
        def mConfig = config.jsonExtractor.configs['two']
        def shortObject = mConfig.shortObjectClosure.call([:])

        then:
         2                            == config.jsonExtractor.configs.size()
         ['person.name':'bar']        == mConfig.dottedRenamedPaths
         ['person.address']           == mConfig.dottedFlattenedPaths
         ['person.customer']          == mConfig.dottedShortObjectPaths
         ['lastName':'Smith']         == mConfig.dottedValuePaths
         'short'                      == shortObject
         ['date']                     == mConfig.dottedDatePaths
         true                         == mConfig.lenientDates
         ['yyyy.MM.dd', 'yyyy/MM/dd'] == mConfig.dateFormats
    }

    def "Test json extractor creation"() {
        setup:
        def src =
        {
            resource 'things' config {
                representation {
                    mediaTypes = ['application/json']
                    jsonExtractor {
                        property 'person.name' name 'bar'
                        property 'person.address' flatObject true
                        property 'person.customer' shortObject true
                        property 'lastName' defaultValue 'Smith'
                        property 'date' date true
                        lenientDates = true
                        dateFormats = ['yyyy.MM.dd', 'yyyy/MM/dd']
                        shortObject { def v -> 'short' }
                    }
                }
            }
        }

        when:
        def config = RestConfig.parse( grailsApplication, src )
        config.validate()
        def extractor = config.getRepresentation( 'things', 'application/json' ).extractor
        def shortObject = extractor.shortObjectClosure.call([:])

        then:
         ['person.name':'bar']       == extractor.dottedRenamedPaths
         ['person.address']          == extractor.dottedFlattenedPaths
         ['person.customer']         == extractor.dottedShortObjectPaths
         ['lastName':'Smith']        == extractor.dottedValuePaths
         'short'                     == shortObject
         ['date']                    == extractor.dottedDatePaths
         ['yyyy.MM.dd','yyyy/MM/dd'] == extractor.dateFormats
         true                        == extractor.lenientDates
    }

    def "Test json extractor creation from merged configuration"() {
        setup:
        def src =
        {
            jsonExtractorTemplates {
                template 'one' config {
                    property 'one' name 'modOne'
                }

                template 'two' config {
                    property 'two' name 'modTwo'
                }
            }

            resource 'things' config {
                representation {
                    mediaTypes = ['application/json']
                    jsonExtractor {
                        inherits = ['one','two']
                        property 'three' name 'modThree'
                    }
                }
            }
        }

        when:
        def config = RestConfig.parse( grailsApplication, src )
        config.validate()
        def extractor = config.getRepresentation( 'things', 'application/json' ).extractor

        then:
        ['one':'modOne','two':'modTwo','three':'modThree'] == extractor.dottedRenamedPaths
    }

}
