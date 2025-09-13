# Snappy Ruler — Android Task

## Overview

Snappy Ruler is an Android app (Kotlin + Jetpack Compose) that provides a drawing canvas with virtual geometry tools (ruler, set square, protractor, and optional compass). Tools snap intelligently to the grid, anchors, and common angles, enabling quick, accurate construction.

## Architecture

* **UI (Compose)**: Canvas rendering, rulers, tool overlays, gesture handling.
* **ViewModel (MVVM)**: Holds shapes, interaction state (zoom/pan), active tool, and undo/redo stack.
* **Domain**: Pure Kotlin modules for snapping and geometry calculations.
* **Data**: Persistence (JSON/Room) and export manager.

Data flow: `UI events → ViewModel → Domain (snap/geom) → ViewModel updates StateFlow → UI re-renders`.

## File Structure

```
ui/          → Canvas, rulers, overlays, HUD, tool picker
viewmodel/   → DrawingViewModel
model/       → Shape, ToolType, InteractionState
domain/      → GeometryUtils, TransformUtils, SnapEngine
data/        → Persistence, ExportManager
MainActivity.kt
```

## Core Features

* **Drawing tools**: Ruler (lines), Set Square, Protractor, Compass (optional).
* **Gestures**: One-finger draw, two-finger pan/zoom/rotate, long-press to toggle snapping.
* **Snapping**: To grid, endpoints, midpoints, intersections, and angles (0°, 30°, 45°, 60°, 90°).
* **Precision HUD**: Live length/angle display.
* **Undo/Redo**: At least 20 steps.
* **Export**: Save drawing as PNG/JPEG.

## Snapping Strategy

* Candidate types: GridPoint, ShapeAnchor, Intersection, Angle.
* Snap radius: dynamic, smaller at high zoom, larger at low zoom.
* Selection: closest + priority (anchors > intersections > grid > angles).
* Feedback: visual highlight + haptic tick.

## Rulers & Calibration

* Top and left rulers like Photoshop.
* Tick spacing adapts to zoom.
* Calibration flow for accurate mm/cm units.

## Deliverables

1. APK + Source (Git repo)
2. README (this file)
3. Demo video (2–3 min) showing tools, snapping, undo/redo, export
4. (Optional) Unit tests for geometry helpers

## Performance Targets

* 60fps while manipulating tools.
* ±0.5° angle precision.
* Length within 1mm accuracy.
* Efficient spatial indexing for snaps.

## Next Steps

* Extend snapping (arcs, tangents).
* Multi-selection transforms.
* Smart alignment guides.
* User preferences for snap strength.

---

**Summary:** Snappy Ruler provides a precise, interactive drawing environment with smart geometry tool snapping, implemented in Jetpack Compose with clean MVVM architecture.
