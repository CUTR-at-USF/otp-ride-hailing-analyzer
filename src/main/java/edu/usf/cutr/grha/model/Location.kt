/*
 * Copyright (C) 2021 University of South Florida
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.usf.cutr.grha.model

import com.univocity.parsers.annotations.Parsed

data class Location
(
    @Parsed val provider:String = "",
    @Parsed val latitude: Double = 0.0,
    @Parsed val longitude: Double = 0.0,
    @Parsed val altitude: Double = 0.0,
    @Parsed val speed: Double = 0.0,
    @Parsed val accuracy: Double = 0.0,
    @Parsed val timeInMs: Long = 0
)
