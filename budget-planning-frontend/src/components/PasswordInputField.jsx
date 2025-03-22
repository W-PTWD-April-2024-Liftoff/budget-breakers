import InputField from "./InputField";

const PasswordInputField = ({ label, name, value, setFormData }) => {
    return <InputField label={label} name={name} value={value} setFormData={setFormData} type="password" />;
};

export default PasswordInputField;