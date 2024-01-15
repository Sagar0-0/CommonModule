Created by [SAGAR MALHOTRA](sagar.0dev@gmail.com)

# Introduction

This document lists the recommendations or guidelines applied to ASTA, from MBITS Innovations.

If you find any error on this document or any inconsistency between the recommendations presented here and the automatic checkstyle and formatter, please open an issue.

## Layout of the Recommendations

Each recommendation is presented as a rule followed by examples

## Recommendations Importance

In this document the words _must_, _should_ and _can_ are used to convey the importance of a recommendation. A _must_ requirement has to be followed, a _should_ is a strong recommendation and a _can_ is a general suggestion.

### Automatic Style Checking and Formatting

TODO

# General Recommendations

Any violation to the guide is allowed if it enhances one of the following, by order of importance (higher levels cannot be sacrificed for lower levels):
* Logical Structure
* Consistency
* Readability
* Ease of modifications

# Naming Conventions
## General Naming Conventions
### All names should be written in English.
### Package names should be in all lower case.
* `com.company.application.ui`
* `com.sun.eng`
* `edu.cmu.cs.bovik.cheese`
### Class names must be nouns, in mixed case with the first letter of each internal word capitalized.
* `class Line;`
* `class AudioSystem;`
### Variable names must be in mixed case starting with lower case.
* `int age;`
* `float availableWidth;`
### Non-public, non-static field names should start with m.
(Hungarian Notation)
* `private long mLongVariable;`
* `private int mIntVariable;`
### Static field names should start with s.
* `private static MyClass sSingleton;`
### Constant (final variables) names must be all uppercase using underscore to separate words.
* `public static final int SOME_CONSTANT = 42;`
### Associated constants (final variables) should be prefixed by a common type name.
* `public static final int COLOR_RED = 1;`
* `public static final int COLOR_GREEN = 2;`
* `public static final int COLOR_BLUE = 3; `
### Method names should be verbs in mixed case, with the first letter in lowercase and with the first letter of each internal word capitalized.
* `getName();`
* `computeTotalWidth();`
### Functions (methods with a return) should be named after what they return and procedures (void methods) after what they do.

### In a method name, the name of the object is implicit and should be avoided.
* `employee.getName();`// NOT: <del>employee.getEmployeeName();</del>
### Negated boolean variable names must be avoided.
* `boolean isLoaded;`// NOT: <DEL> boolean isNotLoaded;</del>
* `boolean isError;`// NOT:<DEL> boolean isNotError;</del>
### Abbreviations in names should be avoided.
* `computeAverage();`// NOT:<DEL> compAvg();</del>
* `ActionEvent event;`// NOT:<DEL> ActionEvent e;</del>
* `catch (Exception exception) {`// NOT:<DEL> catch (Exception e) {</del>
### Abbreviations and acronyms should not be uppercase when used in a name.
* `getCustomerId();`// NOT:<DEL> getCustomerID();</del>
* `exportHtmlSource();`// NOT:<DEL> exportHTMLSource(); </del>
### Generic variables should have the same name as their type.
* `void setView(View view);`// NOT:<DEL> void setView(View v);</del>
* ``// NOT:<DEL> void setView(View aView);</del>
                              
* `void close(Database database);`// NOT:<DEL> void close(Database db);</del>
* // NOT:<DEL> void close(Database sqliteDB);</del>
### The terms get/set must be used where a class attribute is accessed directly.
* `author.getName();`
* `author.setName(name);`

* `point.getX();`
* `point.setX(3);`
### is prefix should be used for boolean variables and methods. Alternatively and if it fits better, has, can and should prefixes can be used.
* `boolean isVisible;`
* `boolean isOpen();`
* `boolean hasLicense();`
* `boolean canEvaluate();`
* `boolean shouldAbort = false;`
### The term compute can be used in methods where something is computed.
* `valueSet.computeAverage();`
* `matrix.computeInverse();`
### The term find can be used in methods where something is looked up.
* `vertex.findNearestVertex();`
* `matrix.findSmallestElement();`
* `node.findShortestPath(Node destinationNode); `
### The term initialize can be used where an object or a concept is set up.
* `initializeViews();`
### Variables with a large scope should have very descriptive (and usually long) names, variables with a small scope can have short names.

### Iterator variables can be called i, j, k, m, n...
```java
for (int i = 0; i < downloads.size(); i++) {
    statements; 
}
```
### Plural form should be used on names representing a collection of objects.
* `ArrayList downloads;`
* `int[] points;`
### num prefix should be used for variables representing a number of objects.
* `int numPoints = points.length();`
### Number suffix should be used for variables representing an entity number.
* `int employeeNumber;`
* `int comicNumber;`
### Exception classes should have Exception like a suffix.
```java 
class CustomException extends Exception {
    ... 
} 
```
### Singleton classes should return their unique instance through the getInstance method.
```java 
class MySingletonClass {
  private final static MySingletonClass sInstance = new MySingletonClass();

  private MySingletonClass() {
      ...
  }

  public static MySingletonClass getInstance() {  // NOT: get() or instance()...
      return sInstance;
  }
}
```
## Specific Naming Conventions
### String key resources must be all lowercase using underscore to separate words.
* `<string name="good_example_key">Example value</string>  `// NOT:<DEL> <string name="badExampleKey">Example value</string></del>
### XML elements identifiers must be all lowercase using underscore to separate words.
```xml
<TextView android:id="@+id/good_id_example"              // NOT: <TextView android:id="@+id/badIdExample"
    android:layout_width="wrap_content"                              android:layout_width="wrap_content"
    android:layout_height="wrap_content"                             android:layout_height="wrap_content"
    />                                                               />
```
### Activiy names can have Activity like a suffix.
```java
public class ExampleActivity extends Activity {
    ...
}
```
### Test method names should be composed by a name representing what is being tested and a name stating which specific case is being tested, separated with underscore.
* `testMethod_specificCase1`
* `testMethod_specificCase2`

```java
void testIsDistinguishable_protanopia() {
    ColorMatcher colorMatcher = new ColorMatcher(PROTANOPIA)
    assertFalse(colorMatcher.isDistinguishable(Color.RED, Color.BLACK))
    assertTrue(colorMatcher.isDistinguishable(Color.X, Color.Y))
}
```
# Layout Techniques
## Length Line
### File content must be kept within 120 columns.
## Indentation
### Basic indentation should be 4 spaces, without using tabs.
```java
if (condition) {
    statements;
    ...
}
```
### Indentation of any wrapping line should be 8 spaces, without using tabs.
```java
if ((condition1 && condition2)
        || (condition3 && condition4)
        ||!(condition5 && condition6)) {
    doSomethingAboutIt();
}
```
## Braces
### 1TBS (One True Brace Style) must be used. That means:
* Opening brace "{" appears at the end of the same line as the declaration statement.
* Ending brace "}" takes up an entire line by itself and it is intended at the same level that its correspondent opening statement.
* Braces are mandatory, even for single-statements or empty blocks.
```java
class MyClass {
    int func() {
        if (something) {
            // ...
        } else if (somethingElse) {
            // ...
        } else {
            // ...
        }
    }
}
```

```java
// NOT: 
if (condition) body();

if (condition)
    body();
```
## White Spaces
### White space should be used in the following cases:
* After and before operators.
* Before an opening brace.
* After Java reserved words.
* After commas.
* After semicolons in for statements.
* After any comment identifier.
* `a = (b + c) * d;`// NOT: <DEL>a=(b+c)*d</del>

* `if (true) { `// NOT: <DEL>if (true){</del>

* `while (true) {  `// NOT: <DEL>while(true) {</del>

* `doSomething(a, b, c, d); `// NOT: <DEL>doSomething(a,b,c,d);</del>

* `for (i = 0; i < 10; i++) { `// NOT: <DEL>for(i=0;i<10;i++){</del>

* `// This is a comment `// NOT: <DEL>//This is a comment</del>

```java
/**                   // NOT: /**
 * This is a javadoc           *This is a javadoc
 * comment                     *comment
 */                            */
```

## Blank Lines
### Three blank lines should be used in the following circumstances:
* Between sections of a source file.
* Between class and interface definitions.
### Two blank lines should be used between methods.
### One blank line should be used in the following circumstances:
* Between the local variables in a method and its first statement.
* Before a block or single-line comment.
* Between logical sections inside a method, to improve readability
# Control Structures
## if
### The if-else class of statements should have the following form:
```java
if (condition) {
    statements;
}
```

```java
if (condition) {
    statements;
} else {
    statements;
}
```

```java
if (condition) {
    statements;
} else if (condition) {
    statements;
} else {
    statements;
}
```
## for
### The for statement should have the following form:
```java
for (initialization; condition; update) {
    statements;
}
```
## while
### The while statement should have the following form:
```java
while (condition) {
    statements;
}
```
## do-while
### The do-while statement should have the following form:
```java
do {
    statements;
} while (condition);
```
## switch
### The switch statement should have the following form:
```java
switch (condition) {
    case ABC:
        statements;
        // falls through

    case DEF:
        statements;
        break;

    case XYZ:
        statements;
        break;

    default:
        statements;
        break;
}
```
## try-catch
### A try-catch statement should have the following form:
```java
try {
    statements;
} catch (Exception exception) {
    statements;
}
```

```java
try {
    statements;
} catch (Exception exception) {
    statements;
} finally {
    statements;
}
```
# Comments
### All comments should be written in English.
### Comments should not be used to compensate for or explain bad code. Tricky or bad code should be rewritten.
### There should be a white space after any comment identifier.
* `// This is a comment    NOT: //This is a comment`

```java
/**                     NOT: /**
 * This is a javadoc          *This is a javadoc
 * comment                    *comment
 */                           */
```
### Comments should be indented relative to their position in the code.
```java
while (true) {       // NOT: while (true) { 

    // Do something          // Do something
    something();                 something();
}                            }
```
### Javadoc comments should have the following form:
```java
/**
 * Return lateral location of the specified position.
 * If the position is unset, NaN is returned.
 *
 * @param x    X coordinate of position.
 * @param y    Y coordinate of position.
 * @param zone Zone of position.
 * @return     Lateral location.
 * @throws IllegalArgumentException  If zone is <= 0.
 */
public double computeLocation(double x, double y, int zone)
  throws IllegalArgumentException
{
  ...
}
```

### // should be used for all non-Javadoc comments, including multi-line comments.
* `// Comment spanning`
* `// more than one line.`
### All public classes and all public and protected methods within public classes should be documented using Javadoc conventions.
### If a collection of objects can not be qualified with its type, it should be followed by a comment stating the type of its elements.
* `private Vector points; // of Point`
* `private Set shapes; // of Shape`
### For separation comments within differents parts of a file, the following forms should be used (depending on its level of importance):
```java
//*********************
//
//*********************
```

```java
//---------------------
//
//---------------------
```
### TODO and FIXME must be written all in capitals and followed by a colon.
* `// TODO: Calculate the new order            `// NOT: <DEL>// TODO -> Calculate the new order</del>
* `// FIXME: Fix the synchronization algorithm `// NOT: <DEL>fixme: Fix the synchronization algorithm</del>
### The TODO comment should be used to indicate pending tasks, code that is temporary, a short-term solution or good enough but not perfect code.
### The FIXME comment should be used to flag something that is bogus and broken.
# Logging

- All logs should be written in English.
- The use of logs in release should be strictly restricted to the necessary ones.
- Logs should be terse but still understandable.
- Logs must never contain private information or protected content.
- System.out.println() or printf (in native code) must never be used.

## Log levels
- The ERROR level should only be used when something fatal has happened, and it is not recoverable
- The WARNING level should be used when something serious and unexpected happened, but you can recover
- The INFORMATIVE level should be used to note something interesting to most people
- The DEBUG level should be used to note what is happening on the device that could be relevant to investigate and debug unexpected behaviours.
- The VERBOSE level should be used for everything else.
- A DEBUG log should be inside an if (LOCAL_LOGD) block.
```java
if (LOCAL_LOGD) {
    Timber.d("Debugging application");
}
```

- A VERBOSE log should be inside an if (LOCAL_LOGV) block.
```java
if (LOCAL_LOGV) {
    Timber.v("Infroming about current state");
}
```
# File Organization
### The package statement must be the first statement of the file.
### The import statements must follow the package statement. import statements should be sorted by importance, grouped together by packages and leaving one blank line between groups.
The ordering of packages according to their importance is as follows:
* Android imports
* Third parties imports (com, junit, net, org)
* Java imports (java and javax)
* Within each group, the order of imports is alphabetical, considering that capital letters come before lower letters (e.g. Z before a).
```java
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ichi2.utils.DiffEngine;
import com.tomgibara.android.veecheck.util.PrefSettings;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
```
### Imported classes should always be listed explicitly.
```java
import android.app.Activity;      // NOT: import android.app.*;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
```
### Class and Interface declarations should be organized in the following manner:
1. Class/Interface documentation.
1. class or interface statement.
1. Class (static) variables in the order public, protected, package (no access modifier), private.
1. Instance variables in the order public, protected, package (no access modifier), private.
1. Constructors.
1. Methods.
1. Inner classes.
### Android Components (Activity, Service, BroadcastReceiver and ContentProvider) declarations should be organized in the following manner:
1. Component documentation.
1. class statement.
1. Class (static) variables in the order public, protected, package (no access modifier), private.
1. Instance variables in the order public, protected, package (no access modifier), private.
1. Constructors.
1. Lifecycle methods (ordered following the natural lifecycle, from creation to destruction).
1. Other methods.
1. Inner classes.
### Methods should be vertically ordered following the two following criteria:
#### Dependency
If one function calls another, they should be vertically close, and the caller should be above the callee, if at all possible.
#### Conceptual Affinity
Methods that perform similar tasks or have similar naming should be vertically close.
# Miscellaneous
## General
### Each declaration should take up an entire line.
```java
int level;
int size;
```
* // NOT: <del> int level, size;</del>
### Each statement should take up an entire line.
```java
i++;
j++;
```// NOT: <del>i++; j++;</del>
### Static variables or methods must always be accessed through the class name and never through an instance variable.
`AClass.classMethod(); `// NOT: <DEL>anObject.classMethod();</del>
### The incompleteness of split lines must be made obvious.
```java
totalSum = a + b + c +
        d + e;
```
        
```java
method(param1, param2, 
        param3);
```
        
```java
setText("Long line split" + 
        "into two parts."); 
```
### Special characters like TAB and page break must be avoided.
## Types
### Type conversions must always be done explicitly. Never rely on implicit type conversion.
* `floatValue = (int) intValue; `// NOT: <DEL>floatValue = intValue;</del>
### Arrays should be declared with their brackets next to the type.
* `int[] points = new int[20]; `// NOT: <DEL>int points[] = new int[20];</del>
## Variables and Constants
### Variables should be initialized where they are declared and they should be declared in the smallest scope possible.
### Variables must never have dual meaning.
### Floating point variables should always be written with decimal point and at least one decimal.
* `double total = 0.0;   `// NOT: <DEL>double total = 0; </del>
* `double speed = 3.0e8; `// NOT: <DEL>double speed = 3e8; </del>
* `double sum; `
* `sum = (a + b) * 10.0; `
### Floating point variables should always be written with a digit before the decimal point.
* `double probability = 0.5; `// NOT: <DEL>double probability = .5;</del>
### Numerical constants (except, in some cases, -1, 0 and 1) should not be coded directly. Use constants instead.
```java
private static final int TEAM_SIZE = 11;
...
Player[] players = new Player[TEAM_SIZE];
```
* // NOT: <del>Player[] players = new Player[11];</del>
## Operators
### Embedded assignments must be avoided.
* `a = b + c; `// NOT: <DEL>d = (a = b + c) + r;</del>
* `d = a + r;`
### The assignment operator should not be used in a place where it can be easily confused with the equality operator.
```java
// NOT:
if (c++ = d++) {
    ...
}
```
### Parenthesis should be used liberally in expressions involving mixed operators in order to make the precedence clear.
* `if ((a == b) && (c == d)) `// NOT: <DEL>if (a == b && c == d)</del>
### If an expression containing a binary operator appears before the ? in the ternary ?: operator, it should be parenthesized.
* `(x >= 0) ? x : -x; `// NOT: <DEL>x >= 0 ? x : -x;</del>
## Conditionals
### Complex conditional expressions must be avoided. Introduce temporary boolean variables instead.
```java
bool isFinished = (elementNo < 0) || (elementNo > maxElement);
bool isRepeatedEntry = elementNo == lastElement; 
if (isFinished || isRepeatedEntry) {
    ... 
} 
```

```java
// NOT: 
if ((elementNo < 0) || (elementNo > maxElement)|| elementNo == lastElement) {
    ... 
}
```
### In an if statement, the normal case should be put in the if-part and the exception in the else-part
```java
boolean isOk = openDatabase(databasePath);
if (isOk) {
    ... 
} else { 
    ... 
} 
```
### Executable statements in conditionals should be avoided.
```java
InputStream stream = File.open(fileName, "w");
if (stream != null) {
    ...
} 
```

```java
// NOT: 
if (File.open(fileName, "w") != null)) {
    ... 
} 
```
## Loops
### Only loop control statements must be included in the for() construction.
```java
maxim = -1;                               // NOT: for (i = 0, maxim = -1; i < 100; i++) { 
for (i = 0; i < 100; i++) {			      maxim = max(maxim, value[i]);
	maxim = max(maxim, value[i]);	          }
}
```
### Loop variables should be initialized immediately before the loop.
```java
boolean isFound = false;    // NOT: boolean isFound = false;
while (!isFound) {                  ...
    ...                             while (!isFound) {
}                                       ...
                                    }
```
### do-while loops can be avoided.
### The use of break and continue in loops should be avoided.
## Exceptions and Finalizers
### Exceptions must not be ignored without a good explanation.
```java
// NOT:
void setServerPort(String value) {
    try {
        serverPort = Integer.parseInt(value);
    } catch (NumberFormatException e) { 
    }
}
```
### Generic Exception should not be caught except at the root of the stack.
```java
try {
    someComplicatedIOFunction();        // may throw IOException 
    someComplicatedParsingFunction();   // may throw ParsingException 
    someComplicatedSecurityFunction();  // may throw SecurityException 
} catch (IOException exception) {                
    handleIOError();                  
} catch (ParsingException exception) {
    handleParsingError();
} catch (SecurityException exception) {
    handleSecurityError();
}	
```

```java
// NOT:
try {
    someComplicatedIOFunction();        // may throw IOException 
    someComplicatedParsingFunction();   // may throw ParsingException 
    someComplicatedSecurityFunction();  // may throw SecurityException 
} catch (Exception exception) {
    handleError();
}
```
### Finalizers should be avoided.

# Use of Experimental features

We avoid using experimental features in languages as a general rule.

As an exception - it *may be* that an experimental feature is nearing general release - that is the API for it is known to be fixed and stable, at which point use of the feature may be accepted if there is a compelling technical reason to do so.

# Cheat Sheet

What|Naming Convention|GOOD Examples|BAD Examples
----|------|------|-----
Package|lower case|com.company.application.ui|com.company.Application.Ui
Class|nouns, mixed case, starting with uppercase|AudioSystem|audioSystem
Variable|mixed case, starting in lowercase|availableWidth|AvailableWidth, available_width
Non-public, non-static field|mixed case, starting with "m"|mLongVariable|longVariable, LongVariable
Static field|mixed case, starting with "s"|sSingleton|singleton, staticVariable
Constant|uppercase, using underscore to separate words|SOME_CONSTANT|some_constant
Method|verbs, mixed case, starting with lowercase|getName()|get_name()
String key|lowercase, using underscore to separate words|good_example_key|badExampleKey
XML element identifier|lowercase, using underscore to separate words|good_id_example|badIdExample

## Line length
120 characters
## Indentation
### Normal: 4 spaces with no tabs
Wrapping lines: 8 spaces with no tabs
Braces style
1TBS braces style: class declarations, method declarations, block statements
## White spaces
### Before and after: operators
### Before: opening brace
### After: Java reserved words, commas, semicolons in for statements, any comment identifier
## Blank lines
### 3 blank lines:
Between sections of a source file
Between class and interface definitions
### 2 blank lines:
Between methods
### 1 blank line:
Between local variables in a method and its first statement
Before a block or single-line comment
Between logical sections inside a method
## File organization
Copyright/ID comment
package declaration
import statements
## Class organization
Class/Interface documentation
class or interface statement
Class (static) variables in the order public, protected, package (no access modifier), private
Instance variables in the order public, protected, package (no access modifier), private
Constructors
Methods
Inner classes
## Android components organization
Component documentation
class statement
Class (static) variables in the order public, protected, package (no access modifier), private
Instance variables in the order public, protected, package (no access modifier), private
Constructors
Lifecycle methods (ordered following the natural lifecycle, from creation to destruction)
Other methods
Inner classes
## Order of imports
Android imports, third parties imports (alphabetically), Java imports (java and javax)
## Order of method modifiers
public protected private abstract static final transient volatile synchronized native strictfp

# References
* [1] Code Complete, Steve McConnel - Microsoft Press
* [2] Clean Code, Robert C. Martin - Prentice Hall
* [3] Java Coding Style Guide, Achut Reddy - Sun Microsystems, Inc. http://cs.bilgi.edu.tr/pages/standards%5Fproject/java%5FCodingStyle.pdf
* [4] Java Code Conventions http://java.sun.com/docs/codeconv/html/CodeConvTOC.doc.html
* [5] Android Code Style Guidelines for Contributors http://source.android.com/source/code-style.html
* [6] Coding Standards for Java from Geosoft http://geosoft.no/development/javastyle.html


# Commit Style Guidelines for ASTA

### Message Structure
Commit messages should be in the following format:

    #<issue-number> A brief description of changes on one line

***


# Pull Requests Guidelines:
  - Follow the Pull request template.
  - Commit messages should follow this template: `fix #<issue-no> - <short description of the changes>`
  - Pull request title shoud follow this template: `fix: <issue-desc>`
  - Squash all your commits to a single commit.
  - Create a new branch before adding and committing your changes ( This allows you to send multiple Pull Requests )

# Coding Guidelines

## App-specific Guidelines:
1. Define featureRoute() function as the main point of interaction with your feature screens from MainNavHost.
```kotlin
fun NavGraphBuilder.walletRoute()
```
2. NavController.navigateTofeature(xxx,navOptions ? = null) to navigate to that Route
```kotlin
fun NavController.navigateToWallet(navOptions: NavOptions? = null) {
    this.navigate(WALLET_GRAPH_ROUTE, navOptions)
}
```
3. Define Route name only in this same file and use that only
```kotlin
private const val WALLET_GRAPH_ROUTE = "graph_wallet"
```
4. Add this feature route in MainNavHost
```kotlin
MainNavHost.kt
walletRoute()
```

1. Use UiState wrapper for your object state
```kotlin
    private val _state = MutableStateFlow<UiState<WalletResponse>>(UiState.Loading)
    val state = _state.asStateFlow()
```
2. Use featureUiEvent callback method for any UiEvent
```kotlin
fun OrderDetailScreen(
    modifier: Modifier = Modifier,
    orderData: OrderDetailData,
    onUiEvent: (OrderDetailUiEvent) -> Unit
)
```
```kotlin
sealed interface AuthUiEvent {
    data object OnLoginFailed : AuthUiEvent
    data object GetOnboardingData : AuthUiEvent
}
```
3. Use featureDestinations sealed class for defining routes of subfeature screens
```kotlin
internal sealed class SettingDestination(val route: String) {
    data object Main : SettingDestination("ss_main")
    data object Notifications : SettingDestination("ss_notification")
}
```
4. Use only popBackStack() to navigate back from the screen
```kotlin
navController.popBackStack()
```
5. If you are using nested NavHost then also work with nested NavController for navigating in feature Destinations
```kotlin
val nestedNavController = rememberNavController()
```
```kotlin
nestedNavController.navigateToMaps(event.address, event.type)
```

In Api,
Use Response<> wrapper(from common main) to wrap your data for api Status and data
```kotlin
suspend fun putAddress(@Body myAddress: MyAddress): Response<PutAddressResponse>
```
```kotlin
data class Response<T>(
    @SerializedName("status")
    val status: Status = Status(),
    @SerializedName("data")
    val data: T
) {
    @Parcelize
    data class Status(
        @SerializedName("code")
        val code: Int = SUCCESS_STATUS_CODE,
        @SerializedName("msg")
        val msg: String = "",
        @SerializedName("retry")
        val retry: Boolean = false,
        @SerializedName("err")
        val error: String = "",
    ) : Parcelable
}
```

In Repo,
1. Expose ResponseState wrapper for suspend functions.
```kotlin
suspend fun uploadFcmToken(tokenDTO: TokenDTO): ResponseState<TokenResponse>
```
2. Use getApiResponseState{} function for wrapping any data calls
```kotlin
override suspend fun getSavedAddresses(uid: String): ResponseState<List<MyAddress>> {
        return getApiResponseState {
            addressApi.getAddresses(uid)
        }
    }
```
3. Use the ApiErrorHandler class to override the functions and add your own status code Handling(must override super methods in else block).
```kotlin
suspend fun <T> getApiResponseState(
    onSuccess: suspend () -> Unit = {},
    onFailure: suspend (Exception) -> Unit = {},
    errorHandler: ApiErrorHandler = ApiErrorHandler(),
    request: suspend () -> Response<T>
): ResponseState<T>
```
4. Expose Flow<> for observables with non-suspend functions
```kotlin
override fun signInWithCredential(authCredential: AuthCredential): Flow<ResponseState<User>>
```
5. Inject dispatcher and Use withContext(dispatcher) to have main safe suspend calls
```kotlin
class AuthRepoImpl @Inject constructor(
    ...	,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepo
```
6. For the work that needs to be surely done even when user leaves the screen, then use 
```kotlin
externalScope.launch{ //work// }.join()
```

In VM,
1. Use toUiState() to map ResponseState to UiState
```kotlin
_currentAddressState.value = addressResponse.toUiState()
```
2. Expose StateFlow objects using backing property:
```kotlin
private val _putAddressState = MutableStateFlow<UiState<PutAddressResponse>>(UiState.Idle)
val putAddressState = _putAddressState.asStateFlow()
```
3. If possible, use stateIn() to Map Flow<> into stateFlow
```kotlin
val locationPermissionRejectedCount = addressRepo.userPreferences
        .map {
            it.locationPermissionRejectedCount
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0,
        )
```
4. Handle events in repo layer that were sent to viewmodel
```kotlin
    fun setIsLocationEnabled() {
        _isLocationEnabled.value = addressRepo.isLocationEnabled()
        checkPermissionAndUpdateCurrentAddress()
    }

    fun setIsPermissionGranted() {
        _isPermissionGranted.value = addressRepo.isPermissionGranted()
    }
```
5. Always use update to assign new value to a stateflow:
```kotlin
_uiState.update{ oldState->
    newState
}
```
6. Do not pass instances of lifecycle components like context, to avoid memory leaks.
7. Use saveStatehandler to preserve data on process death.

In Compose Ui(IMPORTANT): 
1. Use App prefix for accessing any component from design system
```kotlin
            AppIcon(
                imageVector = icon,
                modifier = Modifier.padding(start = AppTheme.spacing.level2),
                tint = iconTint
            )
```
2. Always Create a separate composable for preview
```kotlin
// Preview Function
@Preview(
    "Light Button",
    heightDp = 1100
)
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 1100
)
@Composable
private fun DefaultPreview() {
    AppTheme {
        Surface {
            AppButtonScreen()
        }
    }
}
```
3. Only pass objects, states, callbacks in params of composable functions.
```kotlin
@Composable
fun OrderDetailScreen(
    orderData: OrderDetailData,
    modifier: Modifier = Modifier,
    onUiEvent: (OrderDetailUiEvent) -> Unit
)
```
4. Larger and reusable functions should be present in separate files.
5. While calling a function, give parameter names if more than 2 exists.
```kotlin
           OrderDetailData(
                amt = 8575,
                cDate = 9353,
                discount = 8530,
                walletPoints = 2893
            )
```
6. Use string from resource file instead of hard coding.
```kotlin
R.string.location_access_required.toStringFromResId(context)//Non-Compose
R.string.location_access_required.toStringFromResId()//Compose
```
7. While creating a function, follow this order for adding params... Mandatory params -> Modifier -> other optional param -> one mandatory callback function(if any).
```kotlin
@Composable
fun OrderDetailScreen(
    orderData: OrderDetailData,
    modifier: Modifier = Modifier,
    onUiEvent: (OrderDetailUiEvent) -> Unit
)
```
8. Observe the data from VM
9. Use Property Drilling(Pass data/event from main parent composable to all lower/children composables to the deepest levels).
Parent -> A -> B -> C -> D
i.e. the data/state required in A,B,C,D should be declared in Parent composable
10. If a compose function have more than 3 events, then wrap them in a sealed interface wrapper.
```kotlin
sealed interfae ProfileUiEvent{
    data object onButtonClick() : ProfileUiEvent
    data classs NameChange(val name: String) : ProfileUiEvent
    data classs GenderChange(val gender: Int) : ProfileUiEvent
}
```
11. Handler all events at a single place and that will be lower common ancestor/highest parent composable.
12. Don't create any object in child composable(always declare in parent).
13. Use two types of loading for any screen(only from design system) - User blocking(covers entire screen and restricts user to perform any action), Non-User-Blocking(just to show some progress and loading indicator).
14. Use level 2 for general padding between elements.
15. Create own custom StateHolders to hold any ui element states
```kotlin
@Stable
data class DialogState<T>(
    val isShown: Boolean,
    val show: () -> Unit,
    val dismiss: () -> Unit,
    val confirm: () -> Unit,
    val current: T,
    val setCurrent: (T) -> Unit,
)

@Stable
data class SettingsScreenState(
    val settings: Settings,
    val snackbarHostState: SnackbarHostState,
    val countDialog: DialogState<Int>,
    val descriptionDialog: DialogState<String>,
)

@Composable
fun rememberSettingScreenState(
    settings: Settings,
    setCount: (Int) -> Unit,
    setDescription: (String) -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): SettingsScreenState {
    var countDialogValue by rememberSaveable {
        mutableStateOf(settings.count)
    }
    var isCountDialogShown by rememberSaveable { mutableStateOf(false) }
    var descriptionDialogValue by rememberSaveable {
        mutableStateOf(settings.description)
    }
    var isDescriptionDialogShown by rememberSaveable { mutableStateOf(false) }

    val showCountDialog = remember(settings.count) {
        {
            countDialogValue = settings.count
            isCountDialogShown = true
        }
    }
    val countDialog = remember(isCountDialogShown, countDialogValue, settings.count) {
        DialogState(
            isShown = isCountDialogShown,
            show = showCountDialog,
            dismiss = { isCountDialogShown = false },
            confirm = {
                setCount(countDialogValue)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Updated Count")
                }
                isCountDialogShown = false
            },
            current = countDialogValue,
            setCurrent = { countDialogValue = it }
        )
    }
    val showDescriptionDialog = remember(settings.description) {
        {
            descriptionDialogValue = settings.description
            isDescriptionDialogShown = true
        }
    }
    val descriptionDialog =
        remember(isDescriptionDialogShown, descriptionDialogValue, settings.description) {
            DialogState(
                isShown = isDescriptionDialogShown,
                show = showDescriptionDialog,
                dismiss = { isDescriptionDialogShown = false },
                confirm = {
                    setDescription(descriptionDialogValue)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Updated Description")
                    }
                    isDescriptionDialogShown = false
                },
                current = descriptionDialogValue,
                setCurrent = { descriptionDialogValue = it }
            )
        }

    return remember(settings, countDialog, descriptionDialog) {
        SettingsScreenState(
            settings = settings,
            snackbarHostState = snackbarHostState,
            countDialog = countDialog,
            descriptionDialog = descriptionDialog,
        )
    }
}

@Composable
fun SettingsScreen(viewModel: StateHolderExampleViewModel) {
    val settings by viewModel.state.collectAsStateAsLifecycle()
    val state = rememberSettingScreenState(
        settings = settings,
        setCount = viewModel::setCount,
        setDescription = viewModel::setDescription
    )

    SettingsScreen(state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(state: SettingsScreenState) {
    Scaffold(snackbarHost = { SnackbarHost(state.snackbarHostState) }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                )
            )
            SettingsRow(
                title = "Count",
                subTitle = state.settings.count.toString(),
                onClick = state.countDialog.show
            )
            SettingsRow(
                title = "Description",
                subTitle = state.settings.description,
                onClick = state.descriptionDialog.show
            )
        }
    }
    CountDialog(dialogState = state.countDialog)
    DescriptionDialog(dialogState = state.descriptionDialog)
}
```
or
```kotlin
@Composable
fun rememberNiaAppState(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    userNewsResourceRepository: UserNewsResourceRepository,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): NiaAppState {
    NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
        networkMonitor,
        userNewsResourceRepository,
    ) {
        NiaAppState(
            navController,
            coroutineScope,
            windowSizeClass,
            networkMonitor,
            userNewsResourceRepository,
        )
    }
}

@Stable
class NiaAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    userNewsResourceRepository: UserNewsResourceRepository,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            FOR_YOU_ROUTE -> FOR_YOU
            BOOKMARKS_ROUTE -> BOOKMARKS
            INTERESTS_ROUTE -> INTERESTS
            else -> null
        }

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    /**
     * The top level destinations that have unread news resources.
     */
    val topLevelDestinationsWithUnreadResources: StateFlow<Set<TopLevelDestination>> =
        userNewsResourceRepository.observeAllForFollowedTopics()
            .combine(userNewsResourceRepository.observeAllBookmarked()) { forYouNewsResources, bookmarkedNewsResources ->
                setOfNotNull(
                    FOR_YOU.takeIf { forYouNewsResources.any { !it.hasBeenViewed } },
                    BOOKMARKS.takeIf { bookmarkedNewsResources.any { !it.hasBeenViewed } },
                )
            }.stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(5_000),
                initialValue = emptySet(),
            )

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination) {
                FOR_YOU -> navController.navigateToForYou(topLevelNavOptions)
                BOOKMARKS -> navController.navigateToBookmarks(topLevelNavOptions)
                INTERESTS -> navController.navigateToInterestsGraph(topLevelNavOptions)
            }
        }
    }

    fun navigateToSearch() = navController.navigateToSearch()
}
```


In Unit Testing,
1. Override BaseTest class, beforeEach and afterEach for viewmodel testing
```kotlin
class TrackViewModelTest : BaseTest() {

    private lateinit var viewModel: TrackViewModel
    private val trackingRepo: TrackingRepo = mockk(relaxed = true)

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(TrackViewModel(trackingRepo))
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }
}    
```
2. Use mockk,Junit5,turbine

# SOLID Principles in Kotlin

In Kotlin, SOLID principles refer to a set of five object-oriented design principles aimed at making software designs more understandable, flexible, and maintainable. These principles are:

1. **Single Responsibility Principle (SRP):**
Classes should be responsible and focus on doing one thing at a time.

   - **Incorrect Example:**
     ```kotlin
     class Employee {
         fun calculatePay() {
             // Calculate employee's pay
         }
         fun saveEmployee(employee: Employee) {
             // Save employee to database
         }
     }
     ```
     In this incorrect example, the `Employee` class handles both calculating pay and database operations, violating SRP.

   - **Correct Example:**
     ```kotlin
     class Employee {
         fun calculatePay() {
             // Calculate employee's pay
         }
     }

     class EmployeeRepository {
         fun saveEmployee(employee: Employee) {
             // Save employee to database
         }
     }
     ```
     Here, responsibilities are separated. The `Employee` class handles pay calculations, while `EmployeeRepository` manages database operations.

2. **Open/Closed Principle (OCP):**
Software entities should be open for extension but closed for modification.
Utilize inheritance, interfaces, and design patterns to achieve this. 

   - **Incorrect Example:**
     ```kotlin
     class GraphicEditor {
         fun drawShape(shape: Shape) {
             when (shape) {
                 is Circle -> drawCircle(shape)
                 is Square -> drawSquare(shape)
             }
         }

         fun drawCircle(circle: Circle) {
             // Draw a circle
         }

         fun drawSquare(square: Square) {
             // Draw a square
         }
     }
     ```
     This violates OCP since the `GraphicEditor` class needs modification to support new shapes.

   - **Correct Example:**
     ```kotlin
     interface Shape {
         fun draw()
     }

     class Circle : Shape {
         override fun draw() {
             // Draw a circle
         }
     }

     class Square : Shape {
         override fun draw() {
             // Draw a square
         }
     }

     class GraphicEditor {
         fun drawShape(shape: Shape) {
             shape.draw()
         }
     }
     ```
     Using an interface (`Shape`) and implementing it in specific shape classes allows extending functionality without modifying `GraphicEditor`.

3. **Liskov Substitution Principle (LSP):**
Objects of a superclass should be replaceable with objects of its subclasses without affecting the correctness of the program.

   - **Incorrect Example:**
     ```kotlin
     open class Bird {
         open fun fly() {
             // Fly
         }
     }

     class Ostrich : Bird() {
         override fun fly() {
             // Ostrich can't fly
         }
     }
     ```
     The `Ostrich` class breaks LSP by not behaving as expected (birds should fly).

   - **Correct Example:**
     ```kotlin
     open class Bird {
         open fun fly() {
             // Fly
         }
     }

     class Ostrich : Bird() {
         override fun fly() {
             throw UnsupportedOperationException("Ostrich can't fly")
         }
     }
     ```
     In this case, the `Ostrich` class follows LSP by explicitly indicating it cannot fly.

4. **Interface Segregation Principle (ISP):**
Clients should not be forced to depend on interfaces they don't use. 
I prefer smaller, cohesive interfaces over larger, monolithic ones. 

   - **Incorrect Example:**
     ```kotlin
     interface Worker {
         fun work()
         fun takeBreak()
     }

     class Programmer : Worker {
         override fun work() {
             // Code
         }

         override fun takeBreak() {
             // Take a break
         }
     }
     ```
     ISP is violated since not all implementing classes need both methods.

   - **Correct Example:**
     ```kotlin
     interface Workable {
         fun work()
     }

     interface Breakable {
         fun takeBreak()
     }

     class Programmer : Workable, Breakable {
         override fun work() {
             // Code
         }

         override fun takeBreak() {
             // Take a break
         }
     }
     ```
     Here, interfaces are segregated into smaller, more focused ones, allowing classes to implement only what they need.

5. **Dependency Inversion Principle (DIP):**
High-level modules should be independent of low-level modules. Both should depend on abstractions. Abstractions should not depend on details; details should depend on abstractions.

   - **Incorrect Example:**
     ```kotlin
     class LightSwitch {
         private val bulb = Bulb()

         fun turnOn() {
             bulb.turnOn()
         }

         fun turnOff() {
             bulb.turnOff()
         }
     }
     ```
     This violates DIP since `LightSwitch` directly depends on `Bulb`.

   - **Correct Example:**
     ```kotlin
     interface Switchable {
         fun turnOn()
         fun turnOff()
     }

     class Bulb : Switchable {
         override fun turnOn() {
             // Turn on bulb
         }

         override fun turnOff() {
             // Turn off bulb
         }
     }

     class LightSwitch(private val device: Switchable) {
         fun turnOn() {
             device.turnOn()
         }

         fun turnOff() {
             device.turnOff()
         }
     }
     ```
     The `LightSwitch` class now depends on the `Switchable` interface, allowing flexibility by injecting different implementations.

Applying SOLID principles results in more maintainable, flexible, and easily extendable Kotlin code.

# Jetpack Compose Guidelines:
1. UI Composition:
 Prefer composition over inheritance for building UI components. 
2. Break down UI into smaller, reusable composable functions. 
### State Management:
1. Use remember/rememberSaveable/derivedStateOf for managing local state within composables.
2. Utilize ViewModel for handling complex UI logic and sharing data across composables.
3. Use shared ViewModel for closely related screen data and events.
4. Accessibility: Consider accessibility best practices for inclusive app development. Eg. defining content description on clickable elements.
### Jetpack Compose Performance and Memory Management Guidelines:
1. Use DisposableEffect to manage resources that need to be cleaned up when a composable is removed from the composition.

# Clean MVVM Architecture with Jetpack Compose:
Separation of Concerns
Model: Represents data entities and business logic.
View: Displays UI components using Jetpack Compose.
ViewModel: Manages UI-related logic and interacts with the Model.

### View
Composables in Jetpack Compose act as the View layer in MVVM.
They observe the ViewModel's state and react to changes.
```kotlin
@Composable
fun UserListScreen(viewModel: UserListViewModel) {
    val userList by viewModel.userList.collectAsStateWithLifecycle()

    // Display user list using Jetpack Compose components
    // ...
}
```
### ViewModel
Contains the presentation logic and business operations.
Manages UI state and exposes data to the View layer.
```kotlin
class UserListViewModel : ViewModel() {
    private val _userList = MutableStateFlow<UiState<User>>(UiState.Idle)
    val userList: StateFlow<List<User>> get() = _userList

    // Load users from repository or data source
    fun loadUsers() {
        // Fetch data from repository and update _userList
    }
}
```
### Model
Represents the data layer, including entities, repositories, and data sources.
Encapsulates data-related operations.
```kotlin
data class User(val id: Int, val name: String)
```
```kotlin
class UserRepository {
    // Methods to interact with user data source
}
```
### Data Flow
Jetpack Compose components observe changes in ViewModel's state using collectAsStateWithLifecycle.
ViewModel interacts with the Model (e.g., repositories) to fetch and manipulate data.
The model provides data to the ViewModel, which updates the View accordingly.
State Management
Utilize Jetpack Compose's collectAsStateWithLifecycle or remember to manage and observe state changes from ViewModels.
```kotlin
@Composable
fun UserListScreen(viewModel: UserListViewModel) {
    val userList by viewModel.userList.collectAsStateWithLifecycle()

    // Display user list using Jetpack Compose components based on userList
    // ...
}
```
### Dependency Injection
Use our recommended dependency injection frameworks (Hilt)
```kotlin
@Composable
fun MyApp() {
    val userListViewModel: UserListViewModel = hiltviewModel()
}
```
#### Benefits
Separation of Concerns: Clear separation between UI, logic, and data layers.
Testability: It is easier to test ViewModel and Model independently of UI.
Reactivity: Jetpack Compose's reactive nature simplifies UI updates based on ViewModel changes.



[UI GUIDELINES](https://docs.google.com/document/d/154J2KuX1WiFJCknstjpPaP--fsm3VUqah0L2Am9vyIk/edit?usp=sharing)