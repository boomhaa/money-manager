# Money Manager Design System

## Overview
This design system provides a comprehensive, modern, and beautiful theme for the Money Manager app. It follows Material 3 design principles and includes both light and dark themes.

## Color Palette

### Primary Colors
- **Primary 500**: `#0EA5E9` - Main brand color (Sky Blue)
- **Primary 100**: `#E0F2FE` - Light variant for containers
- **Primary 900**: `#0C4A6E` - Dark variant for text on light backgrounds

### Secondary Colors
- **Secondary 500**: `#EA580C` - Orange accent color
- **Secondary 100**: `#FFEDD5` - Light variant
- **Secondary 900**: `#7F1D1D` - Dark variant

### Success Colors (Income)
- **Success 500**: `#22C55E` - Green for income/positive values
- **Success 100**: `#DCFCE7` - Light variant
- **Success 900**: `#14532D` - Dark variant

### Error Colors (Expense)
- **Error 500**: `#EF4444` - Red for expenses/negative values
- **Error 100**: `#FFFEE4` - Light variant
- **Error 900**: `#7F1D1D` - Dark variant

### Neutral Colors
- **Neutral 50**: `#FAFAFA` - Lightest background
- **Neutral 900**: `#171717` - Darkest text
- **Neutral 300**: `#D4D4D4` - Borders and dividers
- **Neutral 700**: `#404040` - Secondary text

## Typography

### Display Styles
- **Display Large**: 57sp, Light weight - For hero text
- **Display Medium**: 45sp, Light weight - For large headings
- **Display Small**: 36sp, Light weight - For section headers

### Headline Styles
- **Headline Large**: 32sp, Normal weight - For page titles
- **Headline Medium**: 28sp, Normal weight - For section titles
- **Headline Small**: 24sp, Normal weight - For card titles

### Title Styles
- **Title Large**: 22sp, Medium weight - For important labels
- **Title Medium**: 16sp, Medium weight - For form labels
- **Title Small**: 14sp, Medium weight - For small labels

### Body Styles
- **Body Large**: 16sp, Normal weight - For main content
- **Body Medium**: 14sp, Normal weight - For secondary content
- **Body Small**: 12sp, Normal weight - For captions

### Label Styles
- **Label Large**: 14sp, Medium weight - For buttons
- **Label Medium**: 12sp, Medium weight - For small buttons
- **Label Small**: 11sp, Medium weight - For tags

## Spacing System

### Base Spacing (4dp grid)
- **1x**: 4dp
- **2x**: 8dp
- **3x**: 12dp
- **4x**: 16dp
- **5x**: 20dp
- **6x**: 24dp
- **8x**: 32dp
- **10x**: 40dp
- **12x**: 48dp
- **16x**: 64dp
- **20x**: 80dp
- **24x**: 96dp

### Component Spacing
- **Screen Padding**: 16dp
- **Card Padding**: 16dp
- **Card Margin**: 8dp
- **Button Height**: 48dp (small), 56dp (large)
- **Input Field Height**: 56dp

## Components

### BeautifulCard
A card component with elevation, rounded corners, and proper shadows.

```kotlin
BeautifulCard(
    elevation = 4,
    cornerRadius = 16
) {
    // Content
}
```

### BeautifulButton
A styled button with proper colors, elevation, and typography.

```kotlin
BeautifulButton(
    text = "Save",
    onClick = { },
    isPrimary = true
)
```

### BeautifulFAB
A floating action button with proper sizing and elevation.

```kotlin
BeautifulFAB(
    onClick = { },
    icon = Icons.Default.Add
)
```

### BeautifulTextField
A styled text field with proper colors and validation states.

```kotlin
BeautifulTextField(
    value = text,
    onValueChange = { text = it },
    label = "Amount",
    isError = hasError,
    errorMessage = errorText
)
```

### BalanceSummary
A comprehensive balance display with income, expense, and total balance.

### TransactionItem
A styled transaction list item with icons, colors, and actions.

### WelcomeCard
A welcome card with date, greeting, and app branding.

### ShimmerEffect
Loading shimmer effects for better UX during data loading.

## Usage

### Applying the Theme
```kotlin
@Composable
fun MyApp() {
    MoneyManagerTheme {
        // Your app content
    }
}
```

### Using Colors
```kotlin
// In Compose
val primaryColor = MaterialTheme.colorScheme.primary
val successColor = Success500
val errorColor = Error500
```

### Using Typography
```kotlin
Text(
    text = "Hello",
    style = MaterialTheme.typography.headlineMedium
)
```

## Dark Theme Support
The design system automatically adapts to dark theme with:
- Inverted color schemes
- Proper contrast ratios
- System-aware status bar colors
- Dynamic color support (Android 12+)

## Best Practices

1. **Consistency**: Always use the design system components
2. **Accessibility**: Ensure proper contrast ratios and touch targets
3. **Responsiveness**: Use the spacing system for consistent layouts
4. **Performance**: Use the provided components for optimal rendering
5. **Theming**: Test both light and dark themes

## Customization
To customize the theme:
1. Modify colors in `Color.kt`
2. Update typography in `Type.kt`
3. Adjust spacing in `dimens.xml`
4. Create new components following the established patterns
