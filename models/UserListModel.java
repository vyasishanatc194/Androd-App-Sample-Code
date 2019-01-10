package com.mysafetynet.Model;

import java.util.ArrayList;

public class UserListModel {
    private String message;

    private String next_page_url;

    private String total;

    private Result result;

    private String status;

    private String success;

    private String prev_page_url;

    private String current_page;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", next_page_url = " + next_page_url + ", total = " + total + ", result = " + result + ", status = " + status + ", success = " + success + ", prev_page_url = " + prev_page_url + ", current_page = " + current_page + "]";
    }

    public class Result {
        private String total;

        private String to;

        private String next_page_url;

        private String last_page;

        private String per_page;

        private String path;

        private ArrayList<Data> data;

        private String from;

        private String last_page_url;

        private String prev_page_url;

        private String first_page_url;

        private String current_page;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getLast_page() {
            return last_page;
        }

        public void setLast_page(String last_page) {
            this.last_page = last_page;
        }

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
            this.per_page = per_page;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getLast_page_url() {
            return last_page_url;
        }

        public void setLast_page_url(String last_page_url) {
            this.last_page_url = last_page_url;
        }

        public String getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(String prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public String getFirst_page_url() {
            return first_page_url;
        }

        public void setFirst_page_url(String first_page_url) {
            this.first_page_url = first_page_url;
        }

        public String getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(String current_page) {
            this.current_page = current_page;
        }

        @Override
        public String toString() {
            return "ClassPojo [total = " + total + ", to = " + to + ", next_page_url = " + next_page_url + ", last_page = " + last_page + ", per_page = " + per_page + ", path = " + path + ", data = " + data + ", from = " + from + ", last_page_url = " + last_page_url + ", prev_page_url = " + prev_page_url + ", first_page_url = " + first_page_url + ", current_page = " + current_page + "]";
        }
    }

    public class Data {
        private String subscription_id;

        private String thumb_image;

        private String phone;

        private String school_name;

        private String device_type;

        private String state;

        private String id;

        private String first_name;

        private String username;

        private String stripe_id;


        private String age;

        private String gender;

        private String created_at;

        private String otp;

        private String user_image;

        private String parent_id;

        private String school_district_no;

        private String status;

        private String fire_base_token;

        private String deleted_at;

        private String image;

        private String child_age;

        private String order_status;

        private String batch_id;

        private String updated_at;

        private String email;

        private String dob;

        private String last_name;

        public String getSubscription_id() {
            return subscription_id;
        }

        public void setSubscription_id(String subscription_id) {
            this.subscription_id = subscription_id;
        }



        public String getThumb_image() {
            return thumb_image;
        }

        public void setThumb_image(String thumb_image) {
            this.thumb_image = thumb_image;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getStripe_id() {
            return stripe_id;
        }

        public void setStripe_id(String stripe_id) {
            this.stripe_id = stripe_id;
        }


        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getSchool_district_no() {
            return school_district_no;
        }

        public void setSchool_district_no(String school_district_no) {
            this.school_district_no = school_district_no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFire_base_token() {
            return fire_base_token;
        }

        public void setFire_base_token(String fire_base_token) {
            this.fire_base_token = fire_base_token;
        }

        public String getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getChild_age() {
            return child_age;
        }

        public void setChild_age(String child_age) {
            this.child_age = child_age;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getBatch_id() {
            return batch_id;
        }

        public void setBatch_id(String batch_id) {
            this.batch_id = batch_id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        @Override
        public String toString() {
            return "ClassPojo [subscription_id = " + subscription_id + ", thumb_image = " + thumb_image + ", phone = " + phone + ", school_name = " + school_name + ", device_type = " + device_type + ", state = " + state + ", id = " + id + ", first_name = " + first_name + ", username = " + username + ", stripe_id = " + stripe_id + ", age = " + age + ", gender = " + gender + ", created_at = " + created_at + ", otp = " + otp + ", user_image = " + user_image + ", parent_id = " + parent_id + ", school_district_no = " + school_district_no + ", status = " + status + ", fire_base_token = " + fire_base_token + ", deleted_at = " + deleted_at + ", image = " + image + ", child_age = " + child_age + ", order_status = " + order_status + ", batch_id = " + batch_id + ", updated_at = " + updated_at + ", email = " + email + ", dob = " + dob + ", last_name = " + last_name + "]";
        }
    }

}
