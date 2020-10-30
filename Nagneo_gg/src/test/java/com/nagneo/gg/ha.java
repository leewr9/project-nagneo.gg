package com.nagneo.gg;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagneo.vo.ChampionSkillVO;
import com.nagneo.vo.ChampionSkinVO;
import com.nagneo.vo.ChampionVO;
import com.nagneo.vo.ChampionVersionVO;

public class ha {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		search("Shen");

	}

	public static void search(String name) {
		String url = "http://ddragon.leagueoflegends.com/cdn/10.22.1/data/ko_KR/champion/" + name + ".json";
		try {
			ObjectMapper om = new ObjectMapper();
			HttpClient hc = HttpClientBuilder.create().build();
			HttpGet hg = new HttpGet(url);
			HttpResponse hr = hc.execute(hg);
			if (hr.getStatusLine().getStatusCode() == 200) {
				String body = EntityUtils.toString(hr.getEntity(), "UTF-8");
				om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				ChampionVersionVO cvVO = new ChampionVersionVO();
				cvVO = om.readValue(body, ChampionVersionVO.class);
				LinkedHashMap<String, Object> vo = (LinkedHashMap<String, Object>) cvVO.getData().get(name);

				ArrayList<Object> skins = null;
				ArrayList<Object> spells = null;
				LinkedHashMap<String, Object> passive = null;
				LinkedHashMap<String, Object> lhm = null;

				ChampionVO cVO = new ChampionVO();
				ChampionSkillVO cslVO = null;
				ChampionSkinVO cskVO = null;
				ArrayList<ChampionSkillVO> skill = new ArrayList<ChampionSkillVO>();
				ArrayList<ChampionSkinVO> skin = new ArrayList<ChampionSkinVO>();

				int cnt = 1;

				for (String i : vo.keySet()) {
					if (i.equals("id")) {
						cVO.setEngid((String) vo.get(i));
						System.out.println("id =" + vo.get(i));
					}
					if (i.equals("key")) {
						cVO.setKey(Integer.valueOf((String) vo.get(i)));
						System.out.println("key =" + vo.get(i));
					}
					if (i.equals("name")) {
						cVO.setKorid((String) vo.get(i));
						System.out.println("name =" + vo.get(i));
					}
					if (i.equals("title")) {
						cVO.setTitle((String) vo.get(i));
						System.out.println("title =" + vo.get(i));
					}
					if (i.equals("skins")) {
						skins = (ArrayList<Object>) vo.get(i);
					}
					if (i.equals("spells")) {
						spells = (ArrayList<Object>) vo.get(i);
					}
					if (i.equals("passive")) {
						passive = (LinkedHashMap<String, Object>) vo.get(i);
					}
				}
				System.out.println(cVO);
				System.out.println(cVO.getEngid());
				System.out.println(cVO.getKey());

				for (Object i : skins) {
					lhm = (LinkedHashMap<String, Object>) i;
					cskVO = new ChampionSkinVO();
					cskVO.setKey(cVO.getKey());
					for (Object j : lhm.keySet()) {
						if (j.equals("num")) {
							System.out.println("스킨num = " + lhm.get(j));
							cskVO.setNum((Integer) lhm.get(j));
						} else if (j.equals("name")) {
							System.out.println("스킨name = " + lhm.get(j));
							cskVO.setKorid((String) lhm.get(j));
						}
					}
					skin.add(cskVO);
				}

				for (Object i : spells) {
					lhm = (LinkedHashMap<String, Object>) i;
					cslVO = new ChampionSkillVO();
					cslVO.setKey(cVO.getKey());
					cslVO.setNo(cnt);
					for (Object j : lhm.keySet()) {
						if (j.equals("id")) {
							cslVO.setEngid((String) lhm.get(j));
							System.out.println("스킬id = " + lhm.get(j));
						} else if (j.equals("name")) {
							cslVO.setKorid((String) lhm.get(j));
							System.out.println("스킬name = " + lhm.get(j));
						} else if (j.equals("description")) {
							cslVO.setDescription((String) lhm.get(j));
							System.out.println("스킬description = " + lhm.get(j));
						}
					}
					cnt++;
					skill.add(cslVO);
				}

				cslVO = new ChampionSkillVO();
				cslVO.setKey(cVO.getKey());
				cslVO.setNo(cnt);

				for (Object i : passive.keySet()) {
					if (i.equals("image")) {
						lhm = (LinkedHashMap<String, Object>) vo.get(i);
					} else if (i.equals("name")) {
						cslVO.setKorid((String) passive.get(i));
						System.out.println("스킬name = " + lhm.get(i));
					} else if (i.equals("description")) {
						cslVO.setDescription((String) passive.get(i));
						System.out.println("스킬description = " + lhm.get(i));
					}
				}
				

				for (Object i : lhm.keySet()) {
					if (i.equals("full")) {
						cslVO.setEngid((String) lhm.get(i));
						System.out.println("패시브이미지 = " + lhm.get(i));
					}
				}
				
				skill.add(cslVO);
				
				for(ChampionSkillVO i : skill) {
					System.out.println("스킬영어 "+i.getEngid());
					System.out.println("스킬한글 "+i.getKorid());
					System.out.println(i.getDescription());
				}
				
				for(ChampionSkinVO i: skin) {
					System.out.println("스킨이름 "+i.getKorid());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
