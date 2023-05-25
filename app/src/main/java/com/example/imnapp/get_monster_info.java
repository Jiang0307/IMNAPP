package com.example.imnapp;

public class get_monster_info
{
    String introduce, pic_url, name, answer;
//    int[][] answer = new int[15][15];
    get_monster_info(){}

    public get_monster_info(String introduce, String pic_url, String name, String answer)
    {
        this.introduce = introduce;
        this.pic_url = pic_url;
        this.name = name;
        this.answer = answer;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIntroduce()
    {
        return introduce;
    }

    public void setIntroduce(String introduce)
    {
        this.introduce = introduce;
    }

    public String getPic_url()
    {
        return pic_url;
    }

    public void setPic_url(String pic_url)
    {
        this.pic_url = pic_url;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }
}
