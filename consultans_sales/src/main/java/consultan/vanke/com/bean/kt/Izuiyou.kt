package consultan.vanke.com.bean.kt

import java.io.Serializable


data class Izuiyou(
        /**
         * id : 123330537
         * name : 中国🇨🇳军魂
         * avatar_urls : http://tbfile.izuiyou.com/account/view/id/649722816/sz/src
         * content : 过来参加同学聚会，骑了台摩托车过来，暗恋的校花见到后失望极了，说混成这样怎么上楼见人
         * imgs : http://tbfile.izuiyou.com/img/frame/id/666501127?w=540&xcdelogo=0
         * urls : http://dlvideo.izuiyou.com/zyvd/a1/ff/f0a2-7e8e-11e9-8694-00163e02acff
         */
        var id: String? = null,
        var name: String? = null,
        var avatar_urls: String? = null,
        var content: String? = null,
        var imgs: String? = null,
        var urls: String? = null

) : Serializable
//todo这里序列化是否有必要