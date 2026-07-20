'use client';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { useDispatch } from 'react-redux';
import { useLoginMutation } from '@/redux/api/authApi';
import { setCredentials } from '@/redux/slices/authSlice';
import { jwtDecode } from 'jwt-decode'; // npm i jwt-decode

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const [login, { isLoading }] = useLoginMutation();
  const dispatch = useDispatch();
  const router = useRouter();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const res = await login({ email, password }).unwrap();


      const decoded = jwtDecode(res.token);
      const role = decoded.role || res.user?.role;

      dispatch(setCredentials({ user: res.user, token: res.token }));

      
      document.cookie = `token=${res.token}; path=/; max-age=86400`;


      router.push(`/${role}`); // e.g. /admin or /user
    } catch (err) {
      setError(err?.data?.message || 'Invalid email or password');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit}
        className="w-full max-w-sm bg-white p-8 rounded-xl shadow-md space-y-5"
      >
        <h1 className="text-2xl font-semibold text-center">Sign in</h1>

        {error && (
          <p className="text-sm text-red-500 text-center">{error}</p>
        )}

        <div>
          <label className="block text-sm mb-1">Email</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="w-full border rounded-md px-3 py-2 outline-none focus:ring-2 focus:ring-black"
          />
        </div>

        <div>
          <label className="block text-sm mb-1">Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="w-full border rounded-md px-3 py-2 outline-none focus:ring-2 focus:ring-black"
          />
        </div>

        <button
          type="submit"
          disabled={isLoading}
          className="w-full bg-black text-white rounded-md py-2 disabled:opacity-50"
        >
          {isLoading ? 'Signing in...' : 'Login'}
        </button>
      </form>
    </div>
  );
}