<img alt="Icon" src="presentation/src/main/res/mipmap-xxhdpi/ic_launcher.png?raw=true" align="left" hspace="1" vspace="1">

<a alt='Buy Me a Coffee at ko-fi.com' href='https://ko-fi.com/T6T05M4O' target='_blank' align='right'><img align='right' height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi4.png?v=0' border='0' /></a>
<a alt='Try it on Google Play' href='https://play.google.com/store/apps/details?id=com.andremion.theatre' target='_blank' align='right'><img 
align='right' height='36' style='border:0px;height:36px;' src='https://developer.android.com/images/brand/en_generic_rgb_wo_60.png' border='0' /></a>
# Theatre

Pet project using Clean Architecture + MVVM + Reactive Extensions + Android Architecture Components.</br>
The data is fetched from [LondonTheatreDirect API].</br>

#### The main purpose is using the latest practices and libraries.

</br>

[![License Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=true)](http://www.apache.org/licenses/LICENSE-2.0)
![minSdkVersion 16](https://img.shields.io/badge/minSdkVersion-16-red.svg?style=true)
![compileSdkVersion 27](https://img.shields.io/badge/compileSdkVersion-27-yellow.svg?style=true)
[![CircleCI](https://circleci.com/gh/andremion/Theatre.svg?style=svg)](https://circleci.com/gh/andremion/Theatre)
[![codecov](https://codecov.io/gh/andremion/Theatre/graph/badge.svg)](https://codecov.io/gh/andremion/Theatre)

<p align="center">
  <img alt='Sample' src="https://raw.githubusercontent.com/andremion/Theatre/master/art/sample.gif"></br>
  <i>*Data from <a href='https://www.londontheatredirect.com/'>London Theatre Direct</a></i></br>
  <i>**UI inspired by <a href='https://www.uplabs.com/posts/cinema-club-interface'>Yaroslav Zubko</a>'s design</i>
</p>

## Architecture

Uses concepts of the notorious Uncle Bob's architecture called [Clean Architecture].</br>
The software produced by this architecture is going to be:

* Independent of Frameworks.
* Testable.
* Independent of UI.
* Independent of Database.

<img alt='Clean' src="https://raw.githubusercontent.com/andremion/Theatre/master/art/clean.png" align="right" width="50%"/>

### The Dependency Rule

The overriding rule of this architecture says that the source code dependencies always point inwards.</br>
The outer tiers can only dependent of inner tiers. Therefore, the inner tiers know nothing about the outer tiers.</br>
The more further you go through the concentric circles, the higher level the software becomes. Which means that the level of abstraction increases.

### Entities

An entity is a set of data structures. These entities are the business objects of the application and encapsulate the most general and high-level rules, such as [Event] or [Rating].

### Use Cases

They are the operations of the application and may contain specific business rules.</br>
This layer is isolated from database, UI, or any of the common frameworks.</br>
All use case classes extends [UseCase] abstract class that sets up the schedulers of Reactive Extensions.</br>

### Adapters

It is a set of adapters that convert data from the format most convenient for the use cases and entities, to the format most convenient for some external agency such as the UI or Database.</br>
It is this layer that will wholly contain the ViewModels of [MVVM] architectural pattern.</br>
The models are likely just data structures that are passed from the view to the use cases, and vice versa.</br>
Similarly, data is converted, in this layer, from the form most convenient for entities and use cases, into the form most convenient for whatever persistence framework is being used.

### Frameworks

The outermost layer is composed of frameworks and tools such as the Database and the Android Framework.</br>
The Repository pattern is used to encapsulate the details about caching mechanism.

### The Dependency Inversion: WIP

In order to not violate the Dependency Rule, the [Dependency Inversion Principle] must be used whenever complex data needs to be passed across a boundary to an inward layer. Instead of expecting and directly referencing a low-level component (e.g. as a function parameter), the high-level layer provides and references an interface that must be implemented and inherited from by the caller. This way, the conventional dependency relationship is inverted and the high-level layer is decoupled from the low-level component.


### The Inversion of Control Principle and Dependency Injection: WIP

### Modules: WIP

<p align="center">
  <img alt='Modules' src="https://raw.githubusercontent.com/andremion/Theatre/master/art/modules.png"></br>
</p>

#### Data

The **LondonTheatreDirect API** groups the data into:

* __System API:__
_Obtain information about enum data types._

Since the data is basically static, a Repository with database caching is used by Gateway.

* __Inventory API:__
_Obtain realtime information about events on sale, venues, prices and availability._

Due the data volatility, it is used a Repository that caches in memory.

<p align="center">
  <img alt='Data' src="https://raw.githubusercontent.com/andremion/Theatre/master/art/data.png"></br>
</p>

#### Presentation: WIP

<p align="center">
  <img alt='Presentation' src="https://raw.githubusercontent.com/andremion/Theatre/master/art/presentation.png"></br>
</p>

## Credentials

Register your account [here] to get your developer key and put it into [gradle.properties] file

## References

* [Android Clean Architecture](https://github.com/android10/Android-CleanArchitecture)
Sample app that is part of a series of blog posts about how to architect an android application using Uncle Bob's clean architecture approach.
* [Android Architecture Blueprints](https://github.com/googlesamples/android-architecture)
Demonstrate possible ways to help with testing, maintaining and extending of an Android app using different architectural concepts and tools.

## Libraries and tools used in the project

### Android

* [Android Support Library](https://developer.android.com/topic/libraries/support-library/index.html)
Provides additional convenience classes and features not available in the standard Framework API for easier development and support across more devices.
* [Data Binding](https://developer.android.com/topic/libraries/data-binding)
Write declarative layouts and minimize the glue code necessary to bind application logic and layouts.
* [Android KTX](https://github.com/android/android-ktx)
A set of Kotlin extensions for Android app development.

### Architecture and Design

* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
A collection of libraries that help you design robust, testable, and maintainable apps.
Start with classes for managing your UI component lifecycle and handling data persistence.
* [Dagger](https://google.github.io/dagger/)
A fully static, compile-time dependency injection framework for both Java and Android.

### Reactive

* [RX Java](https://github.com/ReactiveX/RxJava)
A library for composing asynchronous and event-based programs using observable sequences for the Java VM.
* [RX Kotlin](https://github.com/ReactiveX/RxKotlin)
RxJava bindings for Kotlin.
* [RX Android](https://github.com/ReactiveX/RxAndroid)
RxJava bindings for Android.

### View and Image

* [ConstraintLayout](https://developer.android.com/training/constraint-layout/index.html)
Allows you to create large and complex layouts with a flat view hierarchy (no nested view groups).
* [RecyclerView](http://developer.android.com/reference/android/support/v7/widget/RecyclerView.html)
A flexible view for providing a limited window into a large data set.
* [Glide](https://github.com/bumptech/glide)
An image loading and caching library for Android focused on smooth scrolling

### Data Request

* [Retrofit](http://square.github.io/retrofit/)
A type-safe HTTP client for Android and Java.
* [OkHttp](http://square.github.io/okhttp/)
An HTTP & HTTP/2 client for Android and Java applications.
* [Moshi](https://github.com/square/moshi)
A modern JSON library for Android and Java.

### Persistence

* [Room](https://developer.android.com/topic/libraries/architecture/room.html)
The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.

### Debugging and tests

* [Stetho](http://facebook.github.io/stetho/)
A debug bridge for Android applications.

## TODO

* [Explain more about the architecture diagrams in README file.](https://github.com/andremion/Theatre/issues/2)
* [Improve and add more unit tests](https://github.com/andremion/Theatre/issues/4)
* [Add pagging on event list](https://github.com/andremion/Theatre/issues/5)

## Contributing

Contributions are always welcome!

**Issues:**
Fell free to open a new issue. Follow the [ISSUE_TEMPLATE.MD](https://github.com/andremion/Theatre/tree/master/ISSUE_TEMPLATE.md)

Follow the "fork-and-pull" Git workflow.

 1. **Fork** the repo on GitHub
 2. **Clone** the project to your own machine
 3. **Commit** changes to your own branch
 4. **Merge** with current *development* branch
 5. **Push** your work back up to your fork
 7. Submit a **Pull request** your changes can be reviewed (please refere the issue if reported)

**Prevent** code-style related changes. Format the code before commiting.

## License

    Copyright 2018 André Mion

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
    
[LondonTheatreDirect API]: https://developer.londontheatredirect.com/ "LondonTheatreDirect API"
[Clean Architecture]: https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html "The Clean Architecture by Robert C. Martin"
[UseCase]: domain/src/main/kotlin/com/andremion/domain/UseCase.kt "Use Case class"
[Event]: domain/src/main/kotlin/com/andremion/domain/entity/Event.kt "Event entity"
[Rating]: domain/src/main/kotlin/com/andremion/domain/entity/Rating.kt "Rating entity"
[MVVM]: https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel "Model–View–ViewModel (MVVM) on Wikipedia"
[Dependency Inversion Principle]: https://en.wikipedia.org/wiki/Dependency_inversion_principle "Dependency Inversion Principle on Wikipedia"
[here]: https://iodocs.londontheatredirect.com/member/register "Register Account on LondonTheatreDirect API"
[gradle.properties]: data/gradle.properties "gradle.properties file"
