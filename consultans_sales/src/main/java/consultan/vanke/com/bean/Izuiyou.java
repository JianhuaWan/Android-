package consultan.vanke.com.bean;

import java.io.Serializable;

public class Izuiyou implements Serializable {

    /**
     * id : 123330537
     * name : 中国🇨🇳军魂
     * avatar_urls : http://tbfile.izuiyou.com/account/view/id/649722816/sz/src
     * content : 过来参加同学聚会，骑了台摩托车过来，暗恋的校花见到后失望极了，说混成这样怎么上楼见人
     * imgs : http://tbfile.izuiyou.com/img/frame/id/666501127?w=540&xcdelogo=0
     * urls : http://dlvideo.izuiyou.com/zyvd/a1/ff/f0a2-7e8e-11e9-8694-00163e02acff
     */

    private String id;
    private String name;
    private String avatar_urls;
    private String content;
    private String imgs;
    private String urls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_urls() {
        return avatar_urls;
    }

    public void setAvatar_urls(String avatar_urls) {
        this.avatar_urls = avatar_urls;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}