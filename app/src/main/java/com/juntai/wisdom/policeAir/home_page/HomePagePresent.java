package com.juntai.wisdom.policeAir.home_page;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.policeAir.AppNetModule;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.bean.CityBean;
import com.juntai.wisdom.policeAir.bean.PolicePickerBean;
import com.juntai.wisdom.policeAir.bean.SearchBean;
import com.juntai.wisdom.policeAir.bean.SearchResultBean;
import com.juntai.wisdom.policeAir.bean.weather.ResponseForcastWeather;
import com.juntai.wisdom.policeAir.bean.weather.ResponseRealTimeWeather;
import com.juntai.wisdom.policeAir.utils.RxScheduler;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @aouther Ma
 * @date 2019/3/14
 */
public class HomePagePresent extends BasePresenter<IModel, HomePageContract.IHomePageView> implements HomePageContract.IHomePagePresent {
    @Override
    protected IModel createModel() {
        return null;
    }


    @Override
    public void userAuth(String tag, RequestBody requestBody) {
        AppNetModule.createrRetrofit()
                .userAuth(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o.message);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    @Override
    public void getWeatherRealTime(String tag, String lng, String lat) {
        AppNetModule.createrRetrofit().getWeatherRealtime(lng, lat).compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<ResponseRealTimeWeather>(getView()) {
            @Override
            public void onSuccess(ResponseRealTimeWeather o) {
                if (getView() != null) {
                    getView().onSuccess(tag, o);
                }

            }

            @Override
            public void onError(String msg) {
                if (getView() != null) {
                    getView().onError(tag, msg);
                }
            }
        });
    }

    @Override
    public void getForcastWeather(String tag, String lng, String lat) {
        AppNetModule.createrRetrofit().getForcast(lng, lat).compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<ResponseForcastWeather>(getView()) {
            @Override
            public void onSuccess(ResponseForcastWeather o) {
                if (getView() != null) {
                    getView().onSuccess(tag, o);
                }

            }

            @Override
            public void onError(String msg) {
                if (getView() != null) {
                    getView().onError(tag, msg);
                }
            }
        });
    }

    @Override
    public void getPrivince(String tag) {
        AppNetModule.createrRetrofit().getProvince().compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<CityBean>(getView()) {
            @Override
            public void onSuccess(CityBean o) {
                if (getView() != null) {
                    getView().onSuccess(tag, o);
                }

            }

            @Override
            public void onError(String msg) {
                if (getView() != null) {
                    getView().onError(tag, msg);
                }
            }
        });
    }

    @Override
    public void getCitys(String tag, String privinceNum) {
        AppNetModule.createrRetrofit().getCity(privinceNum).compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<CityBean>(getView()) {
            @Override
            public void onSuccess(CityBean o) {
                if (getView() != null) {
                    getView().onSuccess(tag, o);
                }

            }

            @Override
            public void onError(String msg) {
                if (getView() != null) {
                    getView().onError(tag, msg);
                }
            }
        });
    }

    @Override
    public void getTowns(String tag, String cityNum) {
        AppNetModule.createrRetrofit().getArea(cityNum).compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<CityBean>(getView()) {
            @Override
            public void onSuccess(CityBean o) {
                if (getView() != null) {
                    getView().onSuccess(tag, o);
                }

            }

            @Override
            public void onError(String msg) {
                if (getView() != null) {
                    getView().onError(tag, msg);
                }
            }
        });
    }

    @Override
    public void getStreets(String tag, String townNum) {
        AppNetModule.createrRetrofit()
                .getStreet(townNum)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CityBean>(getView()) {
                    @Override
                    public void onSuccess(CityBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    @Override
    public void search(String tag, RequestBody requestBody) {
        AppNetModule.createrRetrofit()
                .search(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<SearchBean>(getView()) {
            @Override
            public void onSuccess(SearchBean o) {
                if (getView() != null) {
                    getView().onSuccess(tag, o);
                }

            }

            @Override
            public void onError(String msg) {
                if (getView() != null) {
                    getView().onError(tag, msg);
                }
            }
        });
    }

    @Override
    public void search_getmore(String tag, RequestBody requestBody) {
        AppNetModule.createrRetrofit()
                .search_get_more(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<SearchResultBean>(getView()) {
            @Override
            public void onSuccess(SearchResultBean o) {
                if (getView() != null) {
                    getView().onSuccess(tag, o);
                }

            }

            @Override
            public void onError(String msg) {
                if (getView() != null) {
                    getView().onError(tag, msg);
                }
            }
        });
    }

    @Override
    public void getPolicePickerInfo() {
        AppNetModule.createrRetrofit()
                .getPolicePickerInfo(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUser().getData().getDepartmentId())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<PolicePickerBean>(getView()) {
                    @Override
                    public void onSuccess(PolicePickerBean o) {
                        if (getView() != null) {
                            getView().onSuccess("", o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError("", msg);
                        }
                    }
                });
    }

    /**
     * 获取搜索请求得数据
     *
     * @param keyWord
     * @return
     */
    public RequestBody getSearchRequestBody(String keyWord) {
        FormBody.Builder builder = new FormBody.Builder()
                .add("token", MyApp.getUserToken())
                .add("account", MyApp.getAccount())
                .add("userId", String.valueOf(MyApp.getUid()))
                .add("source", "1") //用户来源（1警小宝；2巡小管；3邻小帮；）
                .add("keyWord", keyWord);
        return builder.build();
    }

    /**
     * 获取搜索更多的请求得数据
     *
     * @param keyWord
     * @param typeId
     * @param pageSize
     * @param startRow 开始的位置
     * @return
     */
    public RequestBody getSearchMoreRequestBody(String keyWord, int typeId, int pageSize, int startRow) {
        FormBody.Builder builder = new FormBody.Builder()
                .add("token", MyApp.getUserToken())
                .add("account", MyApp.getAccount())
                .add("userId", String.valueOf(MyApp.getUid()))
                .add("source", "1") //用户来源（1警小宝；2巡小管；3邻小帮；）
                .add("keyWord", keyWord)
                .add("typeId", String.valueOf(typeId))
                .add("pageSize", String.valueOf(pageSize))
                .add("startRow", String.valueOf(startRow));
        return builder.build();
    }
}
