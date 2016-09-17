# OnboardingLayout

An Android library for creating custom onboarding overlays.

`Beware! This project is in an early development stage. Any public methods and behaviors are subject to change.`

## Usage

### Installing

TBA

### XML

Wrap the content you wish to onboard in an OnboardingLayout.
This is essentially a FrameLayout.

```XML
<se.mattec.onboardinglayout.views.OnboardingLayout
    android:id="@+id/onboarding_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- Insert your views here! -->

</se.mattec.onboardinglayout.views.OnboardingLayout>

```

### Java

Onboarding screens are created using `Onboard`.

```Java
Onboard
    .in(mOnboardingLayout) //Specify in which view to display the onboarding.
    .withText("Look at this cool feature") //Add an onboarding element.
    .below(mViewToShowcase) //Specify the onboarding element placement.
    //Add more onboarding elements here!
    .show();
```

#### Global modifiers

Overlay background color, text color and border color are specified globally.

`withOverlayColor(int backgroundColorResourceId)`

`withTextColor(int textColorResourceId)`

`withBorderColor(int borderColorResourceId)`

#### Onboarding elements

The following onboarding element types are available.

##### Text

`withText(String text)`

###### Allowed placements

* above
* below
* toLeftOf
* toRightOf

##### Text with arrow

`withTextAndArrow(String text, ArrowLocation arrowLocation)`

###### Allowed placements

* above
* below
* toLeftOf
* toRightOf

###### Allowed arrow locations

Depending on the placement, different arrow locations are available. Illegal combinations will cause an exception.

* above, below (placements)
    * left, right, middle (arrow locations)
* left, right
    * above, below

##### Border

`withBorder(boolean isCircular)`

`withDashedBorder(boolean isCircular)`

###### Allowed placements

* around

##### Hole

`withHole(boolean isCircular)`

###### Allowed placements

* around

##### Image

`withImage(int imageResourceId)`

###### Allowed placements

* above
* below
* toLeftOf
* toRightOf
* atop

## Sample application

A sample application is provided  in `/sample`.

APK to be provided.

## Licence

Copyright 2016 Mattias Cederlund 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.