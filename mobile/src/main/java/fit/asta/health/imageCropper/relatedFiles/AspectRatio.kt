package fit.asta.health.imageCropper.relatedFiles

data class AspectRatio(val value: Float) {
    constructor(x: Int, y: Int) : this(y / x.toFloat())
    constructor(x: Float, y: Float) : this(y / x)
}