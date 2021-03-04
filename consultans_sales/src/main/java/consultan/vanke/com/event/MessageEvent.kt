package consultan.vanke.com.event

class MessageEvent<T> {
    var tag = 0
    var data: T? = null
        private set

    constructor() {}
    constructor(tag: Int, data: T) {
        this.tag = tag
        this.data = data
    }

    fun setData(data: T) {
        this.data = data
    }
}