Android-GridContainer
=====================

With the Android `GridView` and `GridLayout` view layouts, it is sometimes hard to customize the grid to fit a specific need.
One such example may be to configure a grid where each row has a different number of views and the potential to specify
different margins and heights for the rows.

The `GridContainer` class provides the capability of laying out views in a custom grid-like configuration. Currently
the grid has the following properties:
- The rows will always use fill width.
- One can specify the number of views to fit per row. The views will share the width equally.
- One can specify an optional margin to use between the views.
- One can specify an optional height for a row. If none is specified, the row will wrap content.

It's also fairly easy to specify different grid configurations for different device orientations:

Portrait:

![Portrait Grid](https://github.com/StephenAsherson/Android-GridContainer/raw/master/screenshots/grid_container_portrait.png)

Landscape:

![Landscape Grid](https://github.com/StephenAsherson/Android-GridContainer/raw/master/screenshots/grid_container_landscape.png)

The `GridContainer` currently only supports the adding of views dynamically from code and not yet from the xml.
The project includes a `TestActivity` class which demonstrates how the `GridContainer` layout can be used.

Disclaimer
-----

The GridContainer project is very basic and currently only fits a specific need. Feel free to take the code and modify as you need for
your purposes.

Who Made It?
-----

Stephen Asherson [http://www.stephenasherson.com]
